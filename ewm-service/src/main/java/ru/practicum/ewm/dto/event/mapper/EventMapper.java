package ru.practicum.ewm.dto.event.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.category.mapper.CategoryMapper;
import ru.practicum.ewm.dto.comment.mapper.CommentListMapper;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.location.mapper.LocationMapper;
import ru.practicum.ewm.dto.user.mapper.UserMapper;
import ru.practicum.ewm.model.Event;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class,
        LocationMapper.class, UserMapper.class, CommentListMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {

    @Mapping(source = "userId", target = "initiator")
    @Mapping(source = "newEventDto.category", target = "category")
    Event dtoToModel(NewEventDto newEventDto, Long userId);

    @Mapping(source = "views", target = "views")
    EventFullDto modelToDto(Event event, Long views);

    @Mapping(source = "views", target = "views")
    EventShortDto modelToShortDto(Event event, Long views);

    @Mapping(source = "eventId", target = "id")
    Event longToModel(Long eventId);

}
