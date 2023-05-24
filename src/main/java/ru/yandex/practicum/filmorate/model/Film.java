package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {                        //Привет, это ТЗ оказалось несложным (если я всё верно понял). Даже помощь не потребовалась (только теория спринта).
    private int id;                        // Проверял через тесты в Postman (показывали в вебинаре как проверить).
    @NotBlank                              // Могу написать тесты сам если это хорошая практика.
    private String name;                   // Доп задание сделал.
    @NotBlank
    @Size(max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
