package ru.practicum.main.event.repository;

import ru.practicum.main.event.dto.EventRequestByParams;
import ru.practicum.main.event.model.Event;

import java.util.List;

public interface CustomEventRepository {
    List<Event> findAllByRequest(EventRequestByParams request);
}
