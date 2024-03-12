package ru.practicum.ewm.dto.event;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.practicum.ewm.annotation.requestStatusSubset.RequestStatusSubset;
import ru.practicum.ewm.dto.request.RequestStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;
    @NotNull
    @RequestStatusSubset(anyOf = {RequestStatus.REJECTED, RequestStatus.CONFIRMED})
    private RequestStatus status;
}
