package ru.practicum.ewm.dto.event.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.mapper.CategoryMapperImpl;
import ru.practicum.ewm.dto.comment.mapper.CommentListMapperImpl;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.event.enums.State;
import ru.practicum.ewm.dto.location.LocationDto;
import ru.practicum.ewm.dto.location.mapper.LocationMapperImpl;
import ru.practicum.ewm.dto.user.UserShortDto;
import ru.practicum.ewm.dto.user.mapper.UserMapperImpl;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventMapperTest {

    private final EventMapper mapper = new EventMapperImpl(new CategoryMapperImpl(),
            new LocationMapperImpl(), new UserMapperImpl(), new CommentListMapperImpl());

    @Test
    void modelToDto() {
        Event event = Event.builder()
                .id(5L)
                .category(Category.builder().id(1L).build())
                .createdOn(LocalDateTime.of(2035, 01, 01, 00, 00, 00))
                .annotation("аннотация")
                .initiator(User.builder().id(2L).build())
                .location(Location.builder().lat(50.5F).build())
                .paid(true)
                .state(State.CANCELED)
                .build();
        EventFullDto eventFullDto = EventFullDto.builder()
                .id(5L)
                .category(CategoryDto.builder().id(1L).build())
                .createdOn(LocalDateTime.of(2035, 01, 01, 00, 00, 00))
                .annotation("аннотация")
                .initiator(UserShortDto.builder().id(2L).build())
                .location(LocationDto.builder().lat(50.5F).build())
                .paid(true)
                .state(State.CANCELED)
                .views(1L)
                .build();

        assertEquals(mapper.modelToDto(event, 1L), eventFullDto);
    }

    @Test
    void dtoToModel() {
        Event event = Event.builder()
                .category(Category.builder().id(1L).build())
                .createdOn(LocalDateTime.now())
                .annotation("аннотация")
                .initiator(User.builder().id(2L).build())
                .location(Location.builder().lat(50.5F).build())
                .paid(true)
                .state(State.PENDING)
                .build();
        NewEventDto newEventDto = NewEventDto.builder()
                .category(1L)
                .annotation("аннотация")
                .location(LocationDto.builder().lat(50.5F).build())
                .paid(true)
                .build();

        assertEquals(mapper.dtoToModel(newEventDto, 1L).getAnnotation(), event.getAnnotation());

    }
}