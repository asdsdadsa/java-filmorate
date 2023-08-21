
package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Показаны пользователи.");
        return userService.getUsers();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Пользователь добавлен, " + user);
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Пользователь обновлён, " + user);
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public User userById(@PathVariable Integer id) {
        log.info("Показан пользователь с " + id + " id.");
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Пользователь с " + id + " добавил в друзья пользователя с " + friendId + ".");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Пользователь с " + id + " удалил из друзей пользователя с " + friendId + ".");
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Показан список друзей.");
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")        //  !!! возвращать значение
    public List<User> getMutualFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Показаны общие друзья.");
        return userService.getMutualFriends(id, otherId);
    }
}

