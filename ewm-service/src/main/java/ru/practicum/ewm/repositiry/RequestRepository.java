package ru.practicum.ewm.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.ParticipationRequest;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByRequesterId(Long userId);

    List<ParticipationRequest> findAllByEventId(Long eventId);

    @Query("SELECT r FROM ParticipationRequest as r " +
            "WHERE r.id IN (?1)")
    List<ParticipationRequest> findAllByIds(List<Long> ids);
}
