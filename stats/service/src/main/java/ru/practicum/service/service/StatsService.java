package ru.practicum.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndPointHit;
import ru.practicum.dto.ViewStat;
import ru.practicum.service.exception.ValidateException;
import ru.practicum.service.mapper.HitMapper;
import ru.practicum.service.model.Stats;
import ru.practicum.service.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatsService {

    private final StatsRepository repository;

    public EndPointHit create(EndPointHit endPointHit) {
        return HitMapper.toDto(repository.save(HitMapper.toHit(endPointHit)));
    }

    @Transactional(readOnly = true)
    public List<ViewStat> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (end.isBefore(start)) throw new ValidateException("Время старта поиска не может быть позже времени окончания.");
        List<Stats> stats;
        if (unique) {
            stats = uris == null || uris.isEmpty() ? repository.findByTimestampBetweenDistinct(start, end) :
                    repository.findByTimestampBetweenAndUriInDistinct(start, end, uris);
        } else {
            stats = uris == null || uris.isEmpty() ? repository.findByTimestampBetween(start, end) :
                    repository.findByTimestampBetweenAndUriInOrderByDesc(start, end, uris);
        }
        return stats.stream().map(s -> new ViewStat(s.getApp(), s.getUri(), s.getHits()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ViewStat> getAllForClient(List<String> uris) {
        return repository.findAllByUriIn(uris).stream()
                .map(s -> new ViewStat(s.getApp(), s.getUri(), s.getHits()))
                .collect(Collectors.toList());
    }
}