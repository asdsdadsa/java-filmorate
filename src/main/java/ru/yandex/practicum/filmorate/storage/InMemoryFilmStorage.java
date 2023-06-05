package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Integer, Film> films = new HashMap<>();
    private int idFilm = 1;

    public Collection<Film> getFilms() {
        return films.values();
    }

    public Film addFilm(Film film) {
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
            return film;
        }
    }

    public Film updateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым.");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года.");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        } else if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Такого id фильма нет.");
        } else {
            films.put(film.getId(), film);
            return film;
        }
    }

    public Film filmById(Integer id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Такого id " + id + " фильма нет.");
        }
        return films.get(id);
    }
}
