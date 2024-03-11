package ru.practicum.ewm.repositiry;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.model.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User AS u WHERE u.id IN (?1) OR (?1) IS NULL")
    List<User> findAllByList(Set<Long> ids, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM User u WHERE u.id = ?1")
    int deleteByIdAndReturnCount(Long userId);
}
