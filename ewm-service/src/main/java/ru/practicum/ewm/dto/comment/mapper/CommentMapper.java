package ru.practicum.ewm.dto.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentForEventDto;
import ru.practicum.ewm.dto.comment.NewCommentDto;
import ru.practicum.ewm.dto.event.mapper.EventMapper;
import ru.practicum.ewm.dto.user.mapper.UserMapper;
import ru.practicum.ewm.model.Comment;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface CommentMapper {

    @Mapping(source = "userId", target = "author.id")
    @Mapping(source = "eventId", target = "event.id")
    Comment dtoToModel(NewCommentDto newCommentDto, Long userId, Long eventId);

    @Mapping(source = "comment.event.id", target = "eventId")
    CommentDto modelToDto(Comment comment);

    CommentForEventDto modelToDtoForEvent(Comment comment);
}
