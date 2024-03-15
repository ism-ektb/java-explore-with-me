package ru.practicum.ewm.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.model.Comment;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment AS c WHERE c.id = ?1")
    int deleteCommentsByIdAndReturnCount(Long commentById);

    @Transactional
    @Modifying
    @Query("DELETE FROM Comment AS c WHERE c.author.id = ?1")
    int deleteCommentsByAuthorIdAndReturnCount(Long userId);

    List<Comment> findAllByEventId(Long eventId);
}
