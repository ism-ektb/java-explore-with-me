package ru.practicum.ewm.dto.event;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.dto.request.RequestStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;
    @NotNull
    private RequestStatus status;
}
