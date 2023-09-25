package ru.yandex.practicum.filmorate.storage.genres;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

public interface GenreStorage {
    Collection<Genre> getGenres();

    Genre genreById(Integer id);

    void addGenreForFilm(Integer filmId, Integer genreId);

    void deleteByFilmId(Integer id);

    Collection<Genre> getGenreFromFilmId(Integer id);
}