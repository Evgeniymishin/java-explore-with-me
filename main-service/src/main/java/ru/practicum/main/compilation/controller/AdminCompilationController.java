package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor

@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {
    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return service.create(newCompilationDto);
    }

    @DeleteMapping("/{compilationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long compilationId) {
        service.delete(compilationId);
    }

    @PatchMapping("/{compilationId}")
    CompilationDto update(@PathVariable Long compilationId,
                          @Valid @RequestBody UpdateCompilationRequest request) {
        return service.update(compilationId, request);
    }
}
