package ru.practicum.main.request.service;

import ru.practicum.main.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto create(Long userId, Long eventId);

    ParticipationRequestDto update(Long userId, Long requestId);

    List<ParticipationRequestDto> getAll(Long userId);
}
