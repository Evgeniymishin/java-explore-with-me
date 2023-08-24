package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Constant;
import ru.practicum.dto.HitDto;
import ru.practicum.model.StatsDto;
import ru.practicum.service.HitService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final HitService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto create(@RequestBody HitDto endPointHit) {
        return service.saveHit(endPointHit);
    }

    @GetMapping("/stats")
    public List<StatsDto> getAll(@DateTimeFormat(pattern = Constant.DATEFORMAT) LocalDateTime start,
                                 @DateTimeFormat(pattern = Constant.DATEFORMAT) LocalDateTime end,
                                 @RequestParam(required = false) List<String> uris,
                                 @RequestParam(defaultValue = "false") boolean unique) {
        return service.getStats(start, end, uris, unique);
    }
}
