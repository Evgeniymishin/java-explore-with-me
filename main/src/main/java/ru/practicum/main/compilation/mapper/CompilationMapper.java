package ru.practicum.main.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public Compilation toCompilation(NewCompilationDto newCompilationDto, Set<Event> events) {
        return Compilation
                .builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.isPinned())
                .events(events)
                .build();
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto
                .builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(compilation.getEvents() == null ? new ArrayList<>() : compilation.getEvents().stream()
                        .map(EventMapper::toShortEventDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Compilation toCompilationUpdated(Compilation compilation, UpdateCompilationRequest request, Set<Event> events) {
        return Compilation
                .builder()
                .id(compilation.getId())
                .title(request.getTitle() == null ? compilation.getTitle() : request.getTitle())
                .pinned(request.getPinned() == null ? compilation.isPinned() : request.getPinned())
                .events(events.isEmpty() ? compilation.getEvents() : events)
                .build();
    }
}
