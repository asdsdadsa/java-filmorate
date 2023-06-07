package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Integer id, Integer friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);

        if (firstUser == null) {
            throw new NotFoundException("Пользователь с таким id " + id + " не найден.");
        }
        if (secondUser == null) {
            throw new NotFoundException("Пользователь с таким id " + friendId + " не найден.");
        }

        firstUser.getFriends().add(secondUser.getId());
        secondUser.getFriends().add(firstUser.getId());
    }

    public void deleteFriend(Integer id, Integer friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);

        if (firstUser == null) {
            throw new NotFoundException("Пользователь с таким id " + id + " не найден.");
        }
        if (secondUser == null) {
            throw new NotFoundException("Пользователь с таким id " + friendId + " не найден.");
        }

        firstUser.getFriends().remove(secondUser.getId());
        secondUser.getFriends().remove(firstUser.getId());
    }

    public List<User> getFriends(Integer id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с таким id " + id + " не найден.");
        }
        List<User> friends = new ArrayList<>();
        for (Integer numberUser : user.getFriends()) {
            friends.add(userStorage.getUserById(numberUser));
        }
        return friends;
    }

    public List<User> getMutualFriends(Integer id, Integer friendId) {
        User firstUser = userStorage.getUserById(id);
        User secondUser = userStorage.getUserById(friendId);
        List<User> mutualFriends = new ArrayList<>();

        if (firstUser == null) {
            throw new NotFoundException("Пользователь с таким id " + id + " не найден.");
        }
        if (secondUser == null) {
            throw new NotFoundException("Пользователь с таким id " + friendId + " не найден.");
        }

        for (Integer numberUser : firstUser.getFriends()) {
            if (secondUser.getFriends().contains(numberUser)) {
                mutualFriends.add(userStorage.getUserById(numberUser));
            }
        }
        return mutualFriends;
    }
}
