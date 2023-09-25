package ru.yandex.practicum.filmorate.storage.genres;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Collection<Genre> getGenres() {
        String sqlQuery = "SELECT * FROM genre";

        final List<Genre> genres = jdbcTemplate.query(sqlQuery, this::mapRowToGenre);

        return genres;
    }

    @Override
    public Genre genreById(Integer id) {
        String sqlQuery = "SELECT * FROM genre WHERE genre_id=?";

        final List<Genre> genres = jdbcTemplate.query(sqlQuery, this::mapRowToGenre, id);

        if (genres.size() < 1) {
            throw new NotFoundException("Ошибка поиска genre с id " + id);
        }

        return genres.get(0);
    }

    @Override
    public Collection<Genre> getGenreFromFilmId(Integer id) {
        final String sqlQuery = "SELECT G.genre_id, G.name\n" +
                "FROM film_genre AS GF\n" +
                "LEFT JOIN genre G on G.genre_id = GF.genre_id\n" +
                "WHERE film_id = ?\n" +
                "ORDER BY  G.genre_id";

        return new HashSet<>(jdbcTemplate.query(sqlQuery, (rs, rowNum) -> mapRowToGenre(rs, rowNum), id));
    }


    @Override
    public void addGenreForFilm(Integer filmId, Integer genreId) {
        final String sqlQuery = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";

        jdbcTemplate.update(sqlQuery, filmId, genreId);
    }

    @Override
    public void deleteByFilmId(Integer id) {
        final String sqlQuery = "DELETE FROM film_genre WHERE film_id = ?";

        jdbcTemplate.update(sqlQuery, id);
    }

    public Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("genre_id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
