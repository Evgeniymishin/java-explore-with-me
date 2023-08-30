package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventRequestByParams;
import ru.practicum.main.event.dto.UpdateEventRequest;

import java.util.List;

public interface AdminEventService {
    EventFullDto update(Long eventId, UpdateEventRequest request);

    List<EventFullDto> getAll(EventRequestByParams request);
}
