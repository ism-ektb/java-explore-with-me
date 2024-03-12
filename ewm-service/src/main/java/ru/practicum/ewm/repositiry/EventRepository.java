package ru.practicum.ewm.repositiry;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.dto.event.enums.State;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long id);

    @Query("SELECT e FROM Event AS e " +
            "WHERE e.initiator.id IN (?1) OR (?1) IS NULL " +
            "AND e.state IN (?2) OR (?2) IS NULL " +
            "AND e.category.id IN (?3) OR (?3) IS NULL " +
            "AND e.createdOn BETWEEN ?4 AND ?5")
    List<Event> findByManyParam(
            List<Long> users, List<State> states, List<Long> categories, LocalDateTime start,
            LocalDateTime end, Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE LOWER(e.annotation) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(e.description) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR (?1) IS NULL " +
            "AND e.category.id IN (?2) OR (?2) IS NULL " +
            "AND e.paid = (?3) OR (?3) IS NULL " +
            "AND e.createdOn BETWEEN ?4 AND ?5 " +
            "AND e.confirmedRequests <= e.participantLimit OR (?6) IS FALSE " +
            "ORDER BY e.createdOn DESC ")
    List<Event> findByManyParams(
            String text, List<Long> categories, Boolean paid, LocalDateTime start,
            LocalDateTime end, Boolean onlyAvailable, Pageable pageable);

    Optional<Event> findByIdAndState(Long eventId, State state);
}
