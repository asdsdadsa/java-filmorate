package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MPA.MPAStorage;

import java.util.Collection;


@Slf4j
@RestController
@RequestMapping("/mpa")
public class MPAController {

    private final MPAStorage mpaStorage;

    public MPAController(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }


    @GetMapping
    public Collection<MPA> getMPA() {
        log.info("Показан список рейтинга.");
        return mpaStorage.getMPA();
    }

    @GetMapping("{id}")
    public MPA MPAById(@PathVariable Integer id) {
        return mpaStorage.MPAById(id);
    }
}
