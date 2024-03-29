package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.Collection;


@RestController
@RequestMapping("/mpa")
public class MPAController {

    private final MPAService mpaService;

    public MPAController(MPAService mpaService) {
        this.mpaService = mpaService;
    }


    @GetMapping
    public Collection<MPA> getMPA() {
        return mpaService.getMPA();
    }

    @GetMapping("{id}")
    public MPA mpaById(@PathVariable Integer id) {
        return mpaService.mpaById(id);
    }
}
