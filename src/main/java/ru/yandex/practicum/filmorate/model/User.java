package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Builder
public class User {
    private Integer id;                                          // int-Integer
    @Email
    private String email;          // Привет, ТЗ оказалось для меня почти таким же как последнее ТЗ в java-kanban (очень сложным).
    @NotBlank                      // С пользователями более менее сам справился, а вот с films и genre уже просил помощи.
    private String login;          // Вроде бы всё получилось. Все тесты в постман с файла пройдены.
    private String name;           // в schema.sql DROP TABLE IF EXISTS наставник посоветовал добавить для тестов в постман
    @PastOrPresent                 // так как при каждом тесте создается еще объект
    private LocalDate birthday;    // со @SpringBootTest тоже вроде как все ясно. Подсказки через // в других классах для себе оставлял.
                                    // Названия вроде бы все в порядке, но мог где-то не досмотреть так как до дедлайна 2 дня и спешу отправить (слишком долго делал это ТЗ)
}

