package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Integer id;                                          // !!! int-Integer
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();              // !!! new HashSet<>()
}
// Привет, проверил ТЗ через тесты Postman и всё прошло успешно. В ТЗ другие заметки я себе написал.
// По поводу тестов, в задании нет указания их делать и я не особо понимаю как. В ТЗ java-kanban был пример в теории, а тут нет.
// Я могу погуглить и сделать по примеру. Но, может быть, потом покажут, как делать (в следующих темах).
// Если это важно и не объяснят, то могу попробовать сделать.
