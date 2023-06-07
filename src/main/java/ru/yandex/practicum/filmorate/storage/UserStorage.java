package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    Collection<User> getUsers();

    User addUser(User user);

    User updateUser(User user);

    User getUserById(Integer id);

    void deleteUser(Integer id);
}

