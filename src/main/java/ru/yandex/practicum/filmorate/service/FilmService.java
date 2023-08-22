package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }


    public void addLike(Integer filmId, Integer userId) {
        log.info("Пользователь с id " + userId + " лайкнул фильм с id " + filmId);
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        log.info("Пользователь с id " + userId + " удалил лайк с фильма с id " + filmId);
        filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> getPopularFilms(Integer size) {
        log.info("Показан список популярных фильмов" + filmStorage.getPopularFilms(size) + ".");
        return filmStorage.getPopularFilms(size);
    }

    public Collection<Film> getFilms() {
        log.info("Показан список фильмов" + filmStorage.getFilms() + ".");
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        log.info("Фильм добавлен, " + film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info("Фильм обновлен, " + film);
        return filmStorage.updateFilm(film);
    }

    public Film filmById(Integer id) {
        log.info("Фильм по id " + id + " получен.");
        return filmStorage.filmById(id);
    }

    public void deleteFilm(Integer id) {
        filmStorage.deleteFilm(id);
    }
}
