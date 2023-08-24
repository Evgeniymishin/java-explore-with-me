package ru.practicum.main.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.repository.CompilationRepository;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public CompilationDto create(NewCompilationDto newCompilationDto) {
        return CompilationMapper.toCompilationDto(compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto,
                newCompilationDto.getEvents() == null ? new ArrayList<>() : eventRepository.findAllById(newCompilationDto.getEvents()))));
    }

    public void delete(Long compilationId) {
        compilationRepository.delete(compilationRepository.findById(compilationId).orElseThrow(() -> new NotFoundException("Не найдена подборка с id = " + compilationId)));
    }

    public CompilationDto update(Long compilationId, UpdateCompilationRequest request) {
        return CompilationMapper.toCompilationDto(compilationRepository.save(CompilationMapper.toCompilationUpdated(
                compilationRepository.findById(compilationId).orElseThrow(() -> new NotFoundException("Не найдена подборка с id = " + compilationId)),
                request,
                request.getEvents() == null ? new ArrayList<>() : eventRepository.findAllById(request.getEvents())
        )));
    }

    @Transactional(readOnly = true)
    public CompilationDto get(Long compilationId) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(compilationId).orElseThrow(() -> new NotFoundException("Не найдена подборка с id = " + compilationId)));
    }

    @Transactional(readOnly = true)
    public List<CompilationDto> getAll(boolean pinned, int from, int size) {
        return compilationRepository.findCompilationsByPinned(pinned, PageRequest.of(from / size, size)).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

}
