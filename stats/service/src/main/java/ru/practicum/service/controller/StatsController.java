package ru.practicum.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.Constant;
import ru.practicum.dto.EndPointHit;
import ru.practicum.dto.ViewStat;
import ru.practicum.service.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndPointHit create(@RequestBody EndPointHit endPointHit) {
        return service.create(endPointHit);
    }

    @Validated
    @GetMapping("/stats")
    public List<ViewStat> getAll(@RequestParam @DateTimeFormat(pattern = Constant.DATEFORMAT) LocalDateTime start,
                                 @RequestParam @DateTimeFormat(pattern = Constant.DATEFORMAT) LocalDateTime end,
                                 @RequestParam(required = false) List<String> uris,
                                 @RequestParam(defaultValue = "false") boolean unique) {
        return service.getAll(start, end, uris, unique);
    }
}
