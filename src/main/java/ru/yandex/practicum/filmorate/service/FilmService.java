package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }


    public void addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.filmById(filmId);
        User user = userStorage.getUserById(userId);

        if (filmId == null) {
            throw new NotFoundException("Фильм с таким id " + filmId + " не найден.");
        }
        if (userId == null) {
            throw new NotFoundException("Пользователь с таким id " + userId + " не найден.");
        }

        film.getLikes().add(user.getId());

    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = filmStorage.filmById(filmId);
        User user = userStorage.getUserById(userId);

        if (filmId == null) {
            throw new NotFoundException("Фильм с таким id " + filmId + " не найден.");
        }
        if (userId == null) {
            throw new NotFoundException("Пользователь с таким id " + userId + " не найден.");
        }

        film.getLikes().remove(user.getId());
    }

    public List<Film> getPopularFilms(Integer size) {
        return filmStorage.getFilms().stream()
                .sorted((p0, p1) -> compare(p0, p1))
                .limit(size)
                .collect(Collectors.toList());
    }

    public int compare(Film p0, Film p1) {
        return p1.getLikes().size() - p0.getLikes().size();
    }
}
