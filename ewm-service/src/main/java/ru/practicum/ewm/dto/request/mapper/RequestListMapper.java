package ru.practicum.ewm.dto.request.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.model.ParticipationRequest;

import java.util.List;

@Mapper(componentModel = "spring", uses = ParticipationRequestMapper.class)
public interface RequestListMapper {

    List<ParticipationRequestDto> modelsToDtos(List<ParticipationRequest> participationRequests);
}
