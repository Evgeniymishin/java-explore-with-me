package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventService {

    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto update(Long userId, Long eventId, UpdateEventRequest request);

    EventRequestStatusUpdateResult updateRequestsStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest requestsForUpdate);

    EventFullDto get(Long userId, Long eventId);

    List<EventShortDto> getAll(Long userId, int from, int size);

    List<ParticipationRequestDto> getAllParticipationsByEvent(Long userId, Long eventId);
}
