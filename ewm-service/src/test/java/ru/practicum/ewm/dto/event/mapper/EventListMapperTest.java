package ru.practicum.ewm.dto.event.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.ViewStatsDto;
import ru.practicum.ewm.dto.category.mapper.CategoryMapperImpl;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.location.mapper.LocationMapperImpl;
import ru.practicum.ewm.dto.user.mapper.UserMapperImpl;
import ru.practicum.ewm.model.Event;

import java.util.List;

class EventListMapperTest {


    @Test
    void modelsToDtos() {

        EventListMapper mapper = new EventListMapper(new EventMapperImpl(new CategoryMapperImpl(), new LocationMapperImpl(),
                new UserMapperImpl()));

        Event event = Event.builder().id(1L).build();
        ViewStatsDto viewStatsDto = ViewStatsDto.builder().uri("event/1").hits(220L).build();
        List<EventShortDto> list = mapper.modelsToDtos(List.of(event), List.of(viewStatsDto));
        Assertions.assertEquals(list.get(0).getId(), 1L);
        Assertions.assertEquals(list.get(0).getViews(), 220L);
    }

    @Test
    void modelsToDtos_BadStat() {

        EventListMapper mapper = new EventListMapper(new EventMapperImpl(new CategoryMapperImpl(), new LocationMapperImpl(),
                new UserMapperImpl()));

        Event event = Event.builder().id(1L).build();
        ViewStatsDto viewStatsDto = ViewStatsDto.builder().uri("event/1tt").hits(220L).build();
        List<EventShortDto> list = mapper.modelsToDtos(List.of(event), List.of(viewStatsDto));
        Assertions.assertEquals(list.get(0).getId(), 1L);
        Assertions.assertEquals(list.get(0).getViews(), 0L);
    }

    @Test
    void modelsToDtos_longUri() {
        EventListMapper mapper = new EventListMapper(new EventMapperImpl(new CategoryMapperImpl(), new LocationMapperImpl(),
                new UserMapperImpl()));

        Event event = Event.builder().id(1L).build();
        ViewStatsDto viewStatsDto = ViewStatsDto.builder().uri("user/event/1").hits(220L).build();
        List<EventShortDto> list = mapper.modelsToDtos(List.of(event), List.of(viewStatsDto));
        Assertions.assertEquals(list.get(0).getId(), 1L);
        Assertions.assertEquals(list.get(0).getViews(), 220L);
    }

    @Test
    void modelsToDtos_isNotStat() {

        EventListMapper mapper = new EventListMapper(new EventMapperImpl(new CategoryMapperImpl(), new LocationMapperImpl(),
                new UserMapperImpl()));

        Event event = Event.builder().id(1L).build();
        ViewStatsDto viewStatsDto = ViewStatsDto.builder().uri("event/5").hits(220L).build();
        List<EventShortDto> list = mapper.modelsToDtos(List.of(event), List.of(viewStatsDto));
        Assertions.assertEquals(list.get(0).getId(), 1L);
        Assertions.assertEquals(list.get(0).getViews(), 0L);
    }
}