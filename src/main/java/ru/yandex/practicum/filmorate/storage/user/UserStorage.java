package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {
    Collection<User> getUsers();

    User addUser(User user);

    User updateUser(User user);

    User getUserById(Integer id);

    void deleteUser(Integer id);

    void addFriend(Integer id, Integer friendId);

    void deleteFriend(Integer id, Integer friendId);

    List<User> getFriends(Integer id);

    List<User> getMutualFriends(Integer id, Integer friendId);
}


