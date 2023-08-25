package ru.practicum.service;

import ru.practicum.dto.HitDto;
import ru.practicum.model.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    HitDto saveHit(HitDto hit);

    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
