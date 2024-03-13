package ru.practicum.ewm.dto.event.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.model.Event;

import java.util.Set;

@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface EventSetMapper {

    Set<Event> setToModels(Set<Long> ids);

    Set<EventShortDto> modelsToDtos(Set<Event> events);
}
