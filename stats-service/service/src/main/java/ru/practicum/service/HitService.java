package ru.practicum.service;

import ru.practicum.dto.HitDto;
import ru.practicum.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    HitDto saveHit(HitDto hit);
    List<ViewStat> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
