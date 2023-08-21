package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

class UserControllerTest {

    private User userTest;

    private User userTest2;

    private User userTest3;
    private UserStorage userStorage = new InMemoryUserStorage();

    private UserService userService = new UserService(userStorage);                        // !!! Порядок очень важен


    private UserController userController = new UserController(new InMemoryUserStorage(), userService);

    @BeforeEach
    public void load() {
        userTest = new User();
        userTest.setId(1);
        userTest.setEmail("test@gmail.com");
        userTest.setLogin("testLogin");
        userTest.setName("testName");
        userTest.setBirthday(LocalDate.of(1901, 1, 1));

        userTest2 = new User();
        userTest2.setId(2);
        userTest2.setEmail("test2@gmail.com");
        userTest2.setLogin("testLogin2");
        userTest2.setName("testName2");
        userTest2.setBirthday(LocalDate.of(1902, 2, 2));

        userTest3 = new User();
        userTest3.setId(3);
        userTest3.setEmail("test3@gmail.com");
        userTest3.setLogin("testLogin3");
        userTest3.setName("testName3");
        userTest3.setBirthday(LocalDate.of(1903, 3, 3));
    }

    @Test
    public void getUsers() {
        userController.addUser(userTest);
        userController.addUser(userTest2);

        Assertions.assertEquals(2, userController.getUsers().size());
    }

    @Test
    public void addUser() {
        userController.addUser(userTest);

        Assertions.assertEquals(1, userController.getUsers().size());
    }

    @Test
    public void updateUser() {
        userController.addUser(userTest);
        userController.updateUser(userTest);

        Assertions.assertEquals(1, userController.getUsers().size());
    }

    @Test
    public void userById() {
        userController.addUser(userTest);
        User user = userController.userById(userTest.getId());

        Assertions.assertEquals(userTest, user);

    }

    @Test
    public void addFriend() {
        userStorage.addUser(userTest);
        userStorage.addUser(userTest2);
        userController.addFriend(userTest.getId(), userTest2.getId());

        Assertions.assertEquals(1, userTest.getFriends().size());
        Assertions.assertEquals(1, userTest2.getFriends().size());
    }

    @Test
    public void deleteFriend() {
        userStorage.addUser(userTest);
        userStorage.addUser(userTest2);
        userController.addFriend(userTest.getId(), userTest2.getId());
        userController.deleteFriend(userTest.getId(), userTest2.getId());

        Assertions.assertEquals(0, userTest.getFriends().size());

    }

    @Test
    public void getFriends() {
        userStorage.addUser(userTest);
        userStorage.addUser(userTest2);
        userController.addFriend(userTest.getId(), userTest2.getId());

        Assertions.assertEquals(1, userTest.getFriends().size());
    }

    @Test
    public void getMutualFriends() {
        userStorage.addUser(userTest);
        userStorage.addUser(userTest2);
        userStorage.addUser(userTest3);
        userController.addFriend(userTest.getId(), userTest3.getId());
        userController.addFriend(userTest2.getId(), userTest3.getId());

        Assertions.assertEquals(1, userController.getMutualFriends(userTest.getId(), userTest2.getId()).size());
        Assertions.assertEquals(1, userController.getMutualFriends(userTest2.getId(), userTest.getId()).size());
    }
}