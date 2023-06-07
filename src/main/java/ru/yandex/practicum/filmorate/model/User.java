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
    private Integer id;                                          // int-Integer
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();              // new HashSet<>()
}
// Добавил delete (в описании ТЗ нет в таблице delete в Controller) в Storage (ты писал про него) и добавил тесты (как понял).
// С @FieldDefaults разобрался (надо добавить @FieldDefaults(level = AccessLevel.PRIVATE) лучше привыкать делать всё так?