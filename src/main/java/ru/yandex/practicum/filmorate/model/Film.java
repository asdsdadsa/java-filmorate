package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)       // private
public class Film {
    Integer id;                 // Спасибо, в фильмах сделал @FieldDefaults чтобы позже использовать.
    @NotBlank                   // В остальных объектах оставил как было, чтобы потом, если буду смотреть для примера проект, не путаться.
    String name;                // Логи перенес в классы сервиса и доделал по рекомендациям.
    @NotBlank                   // Доделал проверку на пустой список в классе FilmDbStorage.
    @Size(max = 200)
    String description;
    @NotNull
    LocalDate releaseDate;
    @Positive
    Integer duration;
    MPA mpa;
    Set<Genre> genres;
}
