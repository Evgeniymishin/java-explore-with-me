package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventRequestByParams;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicEventService {
    EventFullDto get(Long eventId, HttpServletRequest servletRequest);

    List<EventFullDto> getAll(EventRequestByParams request, HttpServletRequest servletRequest);
}
