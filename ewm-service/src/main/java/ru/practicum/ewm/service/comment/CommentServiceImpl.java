package ru.practicum.ewm.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.dto.comment.PatchCommentDto;
import ru.practicum.ewm.dto.comment.mapper.CommentListMapper;
import ru.practicum.ewm.dto.comment.mapper.CommentMapper;
import ru.practicum.ewm.dto.comment.patcher.CommentPatcher;
import ru.practicum.ewm.exception.exception.BaseRelationshipException;
import ru.practicum.ewm.exception.exception.NoFoundObjectException;
import ru.practicum.ewm.model.Comment;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.repositiry.CommentRepository;
import ru.practicum.ewm.service.event.EventService;
import ru.practicum.ewm.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final CommentListMapper listMapper;
    private final CommentPatcher patcher;
    private final EventService eventService;
    private final UserService userService;


    /**
     * Создание нового комментария
     */
    @Override
    public CommentDto create(Long userId, Long eventId, NewCommentDto newCommentDto) {
        //Проверяем входные данные
        Event event = eventService.getAndCheckEventById(eventId);
        userService.getUserByIdIfExist(userId);
        if (event.getInitiator().getId().equals(userId))
            throw new BaseRelationshipException(String.format("Нельзя комментировать свое событие с Id '%s'", eventId));

        //сохраняем
        Comment comment = mapper.dtoToModel(newCommentDto, userId, eventId);
        Comment commentAfterSave = repository.save(comment);
        return mapper.modelToDto(commentAfterSave);
    }

    /**
     * Удаление своего коментания пользователем
     */
    @Override
    public void userDeleteComment(Long userId, Long commentId) {
        Comment comment = checkComment(commentId);
        if (!(comment.getAuthor().getId().equals(userId)))
            throw new BaseRelationshipException(String.format("Комментарий с Id '%s' " +
                    "не принадлежит пользователю с Id: '%s'", commentId, userId));
        repository.deleteById(commentId);

    }

    /**
     * Удаление комментариев администратором
     * Удаление нескольких комментариев по Id
     */
    @Override
    public void adminDeleteComment(Long commentsId) {
        if (repository.deleteCommentsByIdAndReturnCount(commentsId) == 0)
            throw new NoFoundObjectException(String.format("Комментатия с Id '%s' не найдено", commentsId));
    }

    /**
     * Удаление всех комментариев пользователя администратором
     * Удаление нескольких комментариев по Id
     */
    @Override
    public void adminDeleteComments(Long userId) {
        if (repository.deleteCommentsByAuthorIdAndReturnCount(userId) == 0)
            throw new NoFoundObjectException(String.format("Комментариев у пользователя с Id '%s' не найдено", userId));

    }

    /**
     * обновление пользователем комментария
     */
    @Override
    public CommentDto patchComment(Long commentId, Long userId, PatchCommentDto patchCommentDto) {
        Comment comment = checkComment(commentId);
        if (!(comment.getAuthor().getId().equals(userId)))
            throw new BaseRelationshipException(String.format("Комментарий с Id '%s' " +
                    "не принадлежит пользователю с Id: '%s'", commentId, userId));
        Comment commentAfterPatch = patcher.userPatch(comment, patchCommentDto);
        Comment commentAfterSave = repository.save(commentAfterPatch);
        return mapper.modelToDto(commentAfterSave);
    }

    /**
     * получение коментария по Id
     */
    @Override
    public Comment checkComment(Long commentId) {
        return repository.findById(commentId).orElseThrow(() ->
                new NoFoundObjectException(String.format("Комметаний с id: '%s'", commentId)));
    }

    /**
     * Получение списка сомментариев по Id события
     *
     * @param eventId
     */
    @Override
    public List<CommentDto> getListCommentById(Long eventId) {
        eventService.getAndCheckEventById(eventId);
        List<Comment> comments = repository.findAllByEventId(eventId);
        return listMapper.modelsToDtos(comments);
    }
}
