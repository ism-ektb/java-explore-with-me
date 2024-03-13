package ru.practicum.ewm.dto.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.event.mapper.EventMapper;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.dto.user.mapper.UserMapper;
import ru.practicum.ewm.model.ParticipationRequest;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface ParticipationRequestMapper {

    @Mapping(source = "participationRequest.event.id", target = "event")
    @Mapping(source = "participationRequest.requester.id", target = "requester")
    ParticipationRequestDto modelToDto(ParticipationRequest participationRequest);
}
