package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
@Validated
public class PublicCompilationController {
    private final CompilationService service;

    @GetMapping("/{compilationId}")
    CompilationDto get(@PathVariable Long compilationId) {
        return service.get(compilationId);
    }

    @GetMapping
    List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size) {
        return service.getAll(pinned, from, size);
    }
}
