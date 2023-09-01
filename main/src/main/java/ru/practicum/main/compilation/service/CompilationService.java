package ru.practicum.main.compilation.service;

import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto create(NewCompilationDto newCompilationDto);

    void delete(Long compilationId);

    CompilationDto update(Long compilationId, UpdateCompilationRequest request);

    CompilationDto get(Long compilationId);

    List<CompilationDto> getAll(Boolean pinned, int from, int size);
}
