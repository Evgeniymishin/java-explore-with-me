package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Hit;
import ru.practicum.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query(value = "SELECT s.app, s.uri, count(distinct(s.ip)) as hits " +
            "FROM stats s " +
            "WHERE s.uri in :uris AND s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<ViewStat> findByTimestampBetweenAndUriInDistinct(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT s.app, s.uri, count(distinct(s.ip)) as hits " +
            "FROM stats s " +
            "WHERE s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app ",
            nativeQuery = true)
    List<ViewStat> findByTimestampBetweenDistinct(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT s.app, s.uri, count(s.ip) as hits " +
            "FROM stats s " +
            "WHERE s.uri in :uris AND s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<ViewStat> findByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT s.app, s.uri, count(s.ip) as hits " +
            "FROM stats s " +
            "WHERE s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<ViewStat> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT s.app, s.uri, count(distinct(s.ip)) as hits " +
            "FROM stats s " +
            "WHERE s.uri in :uris " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<ViewStat> findAllByUriIn(List<String> uris);
}
