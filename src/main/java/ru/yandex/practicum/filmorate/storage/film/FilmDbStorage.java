package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.MPA.MPAStorage;
import ru.yandex.practicum.filmorate.storage.genres.GenreStorage;
import ru.yandex.practicum.filmorate.storage.likes.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Qualifier("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final MPAStorage mpaStorage;

    private final GenreStorage genreStorage;

    private final LikeStorage likeStorage;

    private final UserStorage userStorage;

    private static final LocalDate EARLIEST_DATE = LocalDate.of(1895, 12, 28); // !!!

    public FilmDbStorage(JdbcTemplate jdbcTemplate, MPAStorage mpaStorage, GenreStorage genreStorage, LikeStorage likeStorage, UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
    }


    @Override
    public Collection<Film> getFilms() {
        final String sqlQuery = "SELECT * FROM films";

        final List<Film> films = jdbcTemplate.query(sqlQuery, this::mapRowToFilm);

        return films;
    }

    @Override
    public Film addFilm(Film film) {
        if (film.getReleaseDate().isBefore(EARLIEST_DATE)) {
            throw new ValidationException("Ошибка даты.");
        }
        final String sqlQuery = "INSERT INTO films(name,description,release_date,duration,mpa_id)" +
                "VALUES(?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        Integer newId = keyHolder.getKey().intValue();
        film.setId(newId);

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            for (Genre genre : film.getGenres()) {
                genreStorage.addGenreForFilm(newId, genre.getId());
            }
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        filmById(film.getId());

        final String sqlQuery = "UPDATE films " +
                "SET name=?,description=?,release_date=?,duration=?,mpa_id=?" +
                "WHERE film_id=?";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),            // getId !!!
                film.getId());

        genreStorage.deleteByFilmId(film.getId());

        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                genreStorage.addGenreForFilm(film.getId(), genre.getId());
            }
        }

        return filmById(film.getId());     // !!!
    }

    @Override
    public Film filmById(Integer id) {
        final String sqlQuery = "SELECT * FROM films WHERE film_id=?";

        final List<Film> films = jdbcTemplate.query(sqlQuery, this::mapRowToFilm, id);

        if (films.isEmpty()) {            // требуется
            throw new NotFoundException("Ошибка поиска фильма с id " + id);
        }

        return films.get(0);
    }

    @Override
    public void deleteFilm(Integer id) {
        final String sqlQuery = "DELETE FROM films WHERE film_id=?";

        jdbcTemplate.update(sqlQuery, id);
    }

    public Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Integer id = resultSet.getInt("film_id");       // чтобы в .genres

        return Film.builder()
                .id(resultSet.getInt("film_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .mpa(mpaStorage.mpaById(resultSet.getInt("MPA_id")))
                .genres((Set<Genre>) genreStorage.getGenreFromFilmId(id))
                .build();
    }


    @Override
    public List<Film> getPopularFilms(Integer size) {

        final String sqlQuery =
                "SELECT f.film_id, f.name, f.description, f.release_date,f.duration, f.mpa_id, COUNT(likes.user_id) " +
                        " FROM films AS f " +
                        " LEFT JOIN likes ON f.film_id = likes.film_id " +
                        " GROUP BY f.film_id, f.name, f.description, f.release_date, f.duration, f.mpa_id " +
                        " ORDER BY COUNT(likes.user_id) DESC " +
                        " LIMIT ?";

        final List<Film> popularFilms = jdbcTemplate.query(sqlQuery, this::mapRowToFilm, size);

        return popularFilms;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        likeStorage.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        filmById(filmId);
        userStorage.getUserById(userId);

        likeStorage.deleteLike(filmId, userId);
    }
}
