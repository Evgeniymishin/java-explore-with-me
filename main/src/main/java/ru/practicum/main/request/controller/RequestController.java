package ru.practicum.main.request.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
@Hidden
public class RequestController {
    private final RequestService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto create(@PathVariable Long userId,
                                   @RequestParam Long eventId) {
        return service.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto update(@PathVariable Long userId,
                                   @PathVariable Long requestId) {
        return service.update(userId, requestId);
    }

    @GetMapping
    List<ParticipationRequestDto> getAll(@PathVariable Long userId) {
        return service.getAll(userId);
    }
}
