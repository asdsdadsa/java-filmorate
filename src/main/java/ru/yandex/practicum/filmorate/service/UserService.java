package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Integer id, Integer friendId) {
        log.info("Пользователь с id " + id + " добавил в друзья пользователя с " + friendId + " id.");
        userStorage.addFriend(id, friendId);
    }

    public void deleteFriend(Integer id, Integer friendId) {
        log.info("Пользователь с id " + id + " удалил из друзей пользователя с " + friendId + " id.");
        userStorage.deleteFriend(id, friendId);
    }

    public List<User> getFriends(Integer id) {
        log.info("Показан список друзей." + userStorage.getFriends(id) + ".");
        return userStorage.getFriends(id);
    }

    public List<User> getMutualFriends(Integer id, Integer friendId) {
        log.info("Показаны общие друзья " + userStorage.getMutualFriends(id, friendId) + ".");
        return userStorage.getMutualFriends(id, friendId);
    }

    public Collection<User> getUsers() {
        log.info("Показаны пользователи " + userStorage.getUsers() + ".");
        return userStorage.getUsers();
    }

    public User addUser(User user) {
        log.info("Пользователь добавлен, " + user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        log.info("Пользователь обновлён, " + user);
        return userStorage.updateUser(user);
    }

    public User getUserById(Integer id) {
        log.info("Показан пользователь с " + id + " id.");
        return userStorage.getUserById(id);
    }

    public void deleteUser(Integer id) {
        userStorage.deleteUser(id);
    }
}
