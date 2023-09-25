package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;


@Component
@Qualifier("UserDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;


    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<User> getUsers() {
        final String sqlQuery = "SELECT * FROM users";

        final List<User> users = jdbcTemplate.query(sqlQuery, this::mapRowToUser);

        return users;
    }

    @Override
    public User addUser(User user) {

        final String sqlQuery = "INSERT INTO users(name,login,email,birthday)" +
                "VALUES(?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            if (user.getName().isBlank() || user.getName().isEmpty()) {
                user.setName(user.getLogin());
                stmt.setString(1, user.getName());
            } else {
                stmt.setString(1, user.getName());
            }
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        getUserById(user.getId());       // требуется

        final String sqlQuery = "UPDATE users SET name = ?, email = ?, login = ?, birthday = ? WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());

        return user;
    }

    @Override
    public User getUserById(Integer id) {
        final String sqlQuery = "SELECT * FROM users WHERE user_id = ?";

        final List<User> users = jdbcTemplate.query(sqlQuery, this::mapRowToUser, id);
        if (users.size() < 1) {
            throw new NotFoundException("Ошибка поиска пользователя с id " + id);
        }

        return users.get(0);
    }

    @Override
    public void deleteUser(Integer id) {
        final String sqlQuery = "DELETE FROM users WHERE user_id=?";

        jdbcTemplate.update(sqlQuery, id);
    }

    public User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("user_id"))
                .name(resultSet.getString("name"))
                .login(resultSet.getString("login"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    // friends
    public void addFriend(Integer id, Integer friendId) {
        getUserById(id);
        getUserById(friendId);

        final String sqlQuery = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";

        jdbcTemplate.update(sqlQuery, id, friendId);

    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        final String sqlQuery = "DELETE FROM friends WHERE user_id=? AND friend_id=?";

        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public List<User> getFriends(Integer id) {

        final String sqlQuery = "SELECT * FROM users WHERE user_id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = ?)";

        final List<User> users = jdbcTemplate.query(sqlQuery, this::mapRowToUser, id);

        return users;
    }

    @Override
    public List<User> getMutualFriends(Integer id, Integer friendId) {

        final String sqlQuery = "SELECT * FROM users WHERE user_id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = ? AND friend_id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = ?))";

        final List<User> users = jdbcTemplate.query(sqlQuery, this::mapRowToUser, id, friendId);

        return users;
    }
}
