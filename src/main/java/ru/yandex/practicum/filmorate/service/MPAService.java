package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MPA.MPAStorage;

import java.util.Collection;

@Service
@Slf4j
public class MPAService {

    private final MPAStorage mpaStorage;

    public MPAService(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Collection<MPA> getMPA() {
        log.info("Показан список рейтинга" + mpaStorage.getMPA() + ".");
        return mpaStorage.getMPA();
    }

    public MPA mpaById(@PathVariable Integer id) {
        log.info("Запрос на получение MPA по id " + id + " получен.");
        return mpaStorage.mpaById(id);
    }
}
