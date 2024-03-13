package ru.practicum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.EndPointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndPointHit, Long> {

    @Query(" SELECT new ru.practicum.model.ViewStats(eh.app, eh.uri, COUNT(DISTINCT eh.ip)) " +
            "FROM EndPointHit eh " +
            "WHERE eh.timestamp BETWEEN ?1 AND ?2 " +
            "AND (eh.uri IN (?3) OR (?3) is NULL) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY eh.uri DESC ")
    List<ViewStats> getStatsIpUnique(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.model.ViewStats(eh.app, eh.uri, COUNT(eh.ip)) " +
            "FROM EndPointHit AS eh " +
            "WHERE eh.timestamp BETWEEN ?1 AND ?2 " +
            "AND (eh.uri IN (?3) OR (?3) is NULL) " +
            "GROUP BY eh.app, eh.uri " +
            "ORDER BY eh.uri DESC")
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}
