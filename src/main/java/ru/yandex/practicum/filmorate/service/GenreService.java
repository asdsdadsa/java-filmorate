package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genres.GenreStorage;

import java.util.Collection;

@Service
@Slf4j
public class GenreService {

    private final GenreStorage genreStorage;

    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Collection<Genre> getGenres() {
        log.info("Показан список жанров" + genreStorage.getGenres() + ".");
        return genreStorage.getGenres();
    }

    public Genre genreById(@PathVariable Integer id) {
        log.info("Запрос на получение жанров по id " + id + " получен.");
        return genreStorage.genreById(id);
    }
}
