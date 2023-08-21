package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

class FilmControllerTest {

    public Film filmTest;
    public Film filmTest2;
    public User userTest;


    private FilmStorage filmStorage = new InMemoryFilmStorage();

    private UserStorage userStorage = new InMemoryUserStorage();

    private FilmService filmService = new FilmService(filmStorage, userStorage);

    private FilmController filmController = new FilmController(filmStorage, filmService);


    @BeforeEach
    public void load() {
        filmTest = new Film();
        filmTest.setId(1);
        filmTest.setName("FilmTest");
        filmTest.setDescription("test");
        filmTest.setReleaseDate(LocalDate.of(2023, 6, 7));
        filmTest.setDuration(5454);

        filmTest2 = new Film();
        filmTest2.setId(2);
        filmTest2.setName("FilmTest2");
        filmTest2.setDescription("test2");
        filmTest2.setReleaseDate(LocalDate.of(2023, 7, 8));
        filmTest2.setDuration(7777);

        userTest = new User();
        userTest.setId(1);
        userTest.setEmail("test@gmail.com");
        userTest.setLogin("testLogin");
        userTest.setName("testName");
        userTest.setBirthday(LocalDate.of(2023, 6, 7));
    }

    @Test
    public void getFilmsTest() {
        filmController.addFilm(filmTest);
        filmController.addFilm(filmTest2);

        Assertions.assertEquals(2, filmController.getFilms().size());
    }

    @Test
    public void addFilmTest() {
        filmController.addFilm(filmTest);

        Assertions.assertEquals(1, filmController.getFilms().size());
    }

    @Test
    public void updateFilmTest() {
        filmController.addFilm(filmTest);
        filmController.updateFilm(filmTest);

        Assertions.assertEquals(1, filmController.getFilms().size());
    }

    @Test
    public void filmByIdTest() {
        filmController.addFilm(filmTest);
        Film film = filmController.filmById(filmTest.getId());

        Assertions.assertEquals(filmTest, film);
    }

    @Test
    public void addLikeTest() {
        userStorage.addUser(userTest);
        filmController.addFilm(filmTest);
        filmController.addLike(filmTest.getId(), 1);

        Assertions.assertEquals(1, filmController.filmById(1).getLikes().size());
    }

    @Test
    public void deleteLikeTest() {
        userStorage.addUser(userTest);
        filmController.addFilm(filmTest);
        filmController.addLike(filmTest.getId(), 1);
        filmController.deleteLike(filmTest.getId(), 1);

        Assertions.assertEquals(0, filmController.filmById(1).getLikes().size());
    }

    @Test
    public void getPopularFilmsTest() {
        userStorage.addUser(userTest);
        filmController.addFilm(filmTest);
        filmController.addLike(filmTest.getId(), 1);

        Assertions.assertEquals(1, filmController.getPopularFilms(1).size());
    }
}
