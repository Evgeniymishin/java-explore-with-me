package ru.practicum.main.event.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.service.PrivateEventService;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
@Validated
@Hidden
public class PrivateEventController {
    private final PrivateEventService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto create(@PathVariable Long userId,
                        @Valid @RequestBody NewEventDto newEventDto) {
        return service.create(userId, newEventDto);
    }

    @PatchMapping("/{eventId}")
    EventFullDto update(@PathVariable Long userId,
                        @PathVariable Long eventId,
                        @Valid @RequestBody UpdateEventRequest request) {
        return service.update(userId, eventId, request);
    }

    @PatchMapping("/{eventId}/requests")
    EventRequestStatusUpdateResult updateRequestsStatus(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @RequestBody EventRequestStatusUpdateRequest request) {
        return service.updateRequestsStatus(userId, eventId, request);
    }

    @GetMapping("/{eventId}")
    EventFullDto get(@PathVariable Long userId,
                     @PathVariable Long eventId) {
        return service.get(userId, eventId);
    }

    @GetMapping
    List<EventShortDto> getAll(@PathVariable Long userId,
                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                               @RequestParam(defaultValue = "10") @Positive int size) {
        return service.getAll(userId, from, size);
    }

    @GetMapping("/{eventId}/requests")
    List<ParticipationRequestDto> getAllParticipationsByEvent(@PathVariable Long userId,
                                                              @PathVariable Long eventId) {
        return service.getAllParticipationsByEvent(userId, eventId);
    }
}
