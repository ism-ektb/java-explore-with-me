package ru.practicum.ewm.dto.comment.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.comment.CommentDto;
import ru.practicum.ewm.dto.comment.CommentForEventDto;
import ru.practicum.ewm.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommentMapper.class)
public interface CommentListMapper {

    List<CommentDto> modelsToDtos(List<Comment> comments);

    List<CommentForEventDto> modelsToDtosForEvent(List<Comment> comments);
}
