package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitDto;
import ru.practicum.mapper.HitMapper;
import ru.practicum.model.StatsDto;
import ru.practicum.repository.HitRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HitServiceImpl implements HitService {
    private final HitRepository repository;

    @Override
    @Transactional
    public HitDto saveHit(HitDto hitDto) {
        return HitMapper.toDto(repository.save(HitMapper.toHit(hitDto)));
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<StatsDto> stats;
        if (unique) {
            stats = uris == null || uris.isEmpty() ? repository.findByTimestampBetweenDistinct(start, end) :
                    repository.findByTimestampBetweenAndUriInDistinct(start, end, uris);
        } else {
            stats = uris == null || uris.isEmpty() ? repository.findByTimestampBetween(start, end) :
                    repository.findByTimestampBetweenAndUriIn(start, end, uris);
        }
        return stats.stream().map(s -> new StatsDto(s.getApp(), s.getUri(), s.getHits()))
                .sorted(Comparator.comparingLong(StatsDto::getHits).reversed())
                .collect(Collectors.toList());
    }

}
