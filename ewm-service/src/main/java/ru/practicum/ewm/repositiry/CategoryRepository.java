package ru.practicum.ewm.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Category;

import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Category c WHERE c.id = ?1")
    int deleteByIdAndReturnCount(Long userId);
}
