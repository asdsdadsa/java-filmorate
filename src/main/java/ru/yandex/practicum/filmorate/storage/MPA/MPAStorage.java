package ru.yandex.practicum.filmorate.storage.MPA;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.Collection;

public interface MPAStorage {
    Collection<MPA> getMPA();

    MPA mpaById(Integer id);
}
