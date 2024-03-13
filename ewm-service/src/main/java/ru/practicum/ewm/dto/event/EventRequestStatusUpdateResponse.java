package ru.practicum.ewm.dto.event;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;

import java.util.List;

@Builder
@Getter
public class EventRequestStatusUpdateResponse {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
