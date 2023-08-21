package ru.yandex.practicum.filmorate.storage.likes;

public interface LikeStorage {

    void addLike(Integer filmId, Integer userId);


    void deleteLike(Integer filmId, Integer userId);

}
