package ru.practicum.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.service.model.Hit;
import ru.practicum.service.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT s.app, s.uri, count(distinct(s.ip)) as hits " +
            "FROM stats s " +
            "WHERE s.uri in :uris AND s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<Stats> findByTimestampBetweenAndUriInDistinct(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT s.app, s.uri, count(distinct(s.ip)) as hits " +
            "FROM stats s " +
            "WHERE s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app ",
            nativeQuery = true)
    List<Stats> findByTimestampBetweenDistinct(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT s.app, s.uri, count(s.ip) as hits " +
            "FROM stats s " +
            "WHERE s.uri in :uris AND s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<Stats> findByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(value = "SELECT s.app, s.uri, count(s.ip) as hits " +
            "FROM stats s " +
            "WHERE s.timestamp BETWEEN :start AND :end " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<Stats> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT s.app, s.uri, count(distinct(s.ip)) as hits " +
            "FROM stats s " +
            "WHERE s.uri in :uris " +
            "GROUP BY s.uri, s.app",
            nativeQuery = true)
    List<Stats> findAllByUriIn(List<String> uris);
}
