package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventRequestByParams;
import ru.practicum.main.event.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventController {
    private final PublicEventService service;

    @GetMapping("/{eventId}")
    EventFullDto get(@PathVariable Long eventId, HttpServletRequest request) {
        return service.get(eventId, request);
    }

    @GetMapping
    List<EventFullDto> getAll(EventRequestByParams request,
                              HttpServletRequest servletRequest) {
        return service.getAll(request, servletRequest);
    }
}
