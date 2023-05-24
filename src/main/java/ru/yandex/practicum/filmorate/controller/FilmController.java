package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private int idFilm = 1;

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Показан список фильмов.");
        return films.values();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым.");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года.");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        } else {
            film.setId(idFilm++);
            films.put(film.getId(), film);
            log.info("Фильм добавлен, " + film);
            return film;
        }
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым.");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года.");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        } else if (!films.containsKey(film.getId())) {
            throw new ValidationException("Такого id фильма нет.");
        } else {
            films.put(film.getId(), film);
            log.info("Фильм обновлен, " + film);
            return film;
        }
    }
}
