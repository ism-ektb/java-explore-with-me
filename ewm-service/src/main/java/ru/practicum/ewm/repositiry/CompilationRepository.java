package ru.practicum.ewm.repositiry;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Compilation;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Query("SELECT c FROM Compilation AS c WHERE c.pinned = ?1 OR (?1) IS NULL")
    List<Compilation> getCompilationByPinned(Boolean pinned, Pageable pageable);
}
