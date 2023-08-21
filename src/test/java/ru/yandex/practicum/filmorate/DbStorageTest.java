package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DbStorageTest {
    private final UserDbStorage userDbStorage;
    private User user;


    @BeforeEach
    public void beforeAll() {
        user = User.builder()
                .id(1)
                .name("Test")
                .login("TestUser")
                .email("test@email.com")
                .birthday(LocalDate.of(2023, Month.AUGUST, 21))
                .build();
        userDbStorage.addUser(user);
    }


    @Test
    public void addUserTest() {
        assertEquals(1, userDbStorage.getUsers().size());
    }


    @Test
    public void updateUser() {
        User updateUser = userDbStorage.updateUser(
                User.builder()
                        .id(1)
                        .name("TestUpdate")
                        .login("TestUserUpdate")
                        .email("testUpdate@email.com")
                        .birthday(LocalDate.of(2023, Month.AUGUST, 20))
                        .build());
        assertEquals(updateUser.getId(), 1);
        assertEquals(updateUser.getName(), "TestUpdate");
        assertEquals(updateUser.getLogin(), "TestUserUpdate");
        assertEquals(updateUser.getEmail(), "testUpdate@email.com");
        assertEquals(updateUser.getBirthday(), LocalDate.of(2023, Month.AUGUST, 20));
    }

    @Test
    public void getUserById() {
        User user1 = userDbStorage.getUserById(user.getId());
        assertEquals(user, user1);
    }


    @Test
    public void addFriend() {

        User friend = User.builder()
                .id(10)
                .name("TestFriend")
                .login("TestUserFriend")
                .email("testFriend@email.com")
                .birthday(LocalDate.of(2023, Month.AUGUST, 22))
                .build();
        userDbStorage.addUser(friend);

        userDbStorage.addFriend(user.getId(), friend.getId());

        assertEquals(1, userDbStorage.getFriends(user.getId()).size());
    }

    @Test
    public void deleteFriend() {
        User friend = User.builder()
                .id(10)
                .name("TestFriend")
                .login("TestUserFriend")
                .email("testFriend@email.com")
                .birthday(LocalDate.of(2023, Month.AUGUST, 22))
                .build();
        userDbStorage.addUser(friend);

        userDbStorage.addFriend(user.getId(), friend.getId());
        userDbStorage.deleteFriend(user.getId(), friend.getId());

        assertEquals(0, userDbStorage.getFriends(user.getId()).size());

    }


    @Test
    public void getMutualFriends() {
        User friend = User.builder()
                .id(10)
                .name("TestFriend")
                .login("TestUserFriend")
                .email("testFriend@email.com")
                .birthday(LocalDate.of(2023, Month.AUGUST, 22))
                .build();
        userDbStorage.addUser(friend);

        User user1 = User.builder()
                .id(10)
                .name("TestFriend")
                .login("TestUserFriend")
                .email("testFriend@email.com")
                .birthday(LocalDate.of(2023, Month.AUGUST, 22))
                .build();
        userDbStorage.addUser(user1);

        userDbStorage.addFriend(user.getId(), friend.getId());
        userDbStorage.addFriend(user1.getId(), friend.getId());

        assertEquals(userDbStorage.getMutualFriends(user.getId(), user1.getId()).size(), 1);
    }

}
