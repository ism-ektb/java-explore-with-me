package ru.practicum.ewm.service.comment;

import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.dto.comment.PatchCommentDto;
import ru.practicum.ewm.model.Comment;

import java.util.List;

public interface CommentService {

    /**
     * Создание нового комментария
     */
    CommentDto create(Long userId, Long eventId, NewCommentDto newCommentDto);

    /**
     * Удаление своего коментания пользователем
     */
    void userDeleteComment(Long userId, Long commentId);

    /**
     * Удаление комментариев администратором
     * Удаление нескольких комментариев по Id
     */
    void adminDeleteComment(Long commentsId);

    /**
     * Удаление всех комментариев пользователя администратором
     * Удаление нескольких комментариев по Id
     */
    void adminDeleteComments(Long userId);

    /**
     * обновление пользователем комментария
     */
    CommentDto patchComment(Long userId, Long commentId, PatchCommentDto patchCommentDto);

    /**
     * получение коментария по Id
     */
    Comment checkComment(Long userId);

    /**
     * Получение списка сомментариев по Id события
     */
    List<CommentDto> getListCommentById(Long eventId);
}
