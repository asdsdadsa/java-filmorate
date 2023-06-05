package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmStorage filmStorage;

    FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }


    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Показан список фильмов.");
        return filmStorage.getFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Фильм добавлен, " + film);
        return filmStorage.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Фильм обновлен, " + film);
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film filmById(@PathVariable Integer id) {
        log.info("Фильм по id " + id + " получен.");
        return filmStorage.filmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Пользователь с id " + userId + " лайкнул фильм с id " + id);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Пользователь с id " + userId + " удалил лайк с фильма с id " + id);
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {          //defaultTValue!!!
        log.info("Показан список популярных фильмов.");
        return filmService.getPopularFilms(count);
    }
}
