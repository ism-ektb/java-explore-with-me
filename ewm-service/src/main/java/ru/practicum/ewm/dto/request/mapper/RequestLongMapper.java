package ru.practicum.ewm.dto.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.request.RequestStatus;
import ru.practicum.ewm.exception.exception.BaseRelationshipException;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.ParticipationRequest;
import ru.practicum.ewm.model.User;

import static ru.practicum.ewm.dto.event.enums.State.CANCELED;
import static ru.practicum.ewm.dto.event.enums.State.PENDING;

@Component
public class RequestLongMapper {

    public ParticipationRequest longToModel(User user, Event event) {
        if ((event.getParticipantLimit() <= event.getConfirmedRequests()) && (event.getParticipantLimit() != 0))
            throw new BaseRelationshipException(String.format("Лимит участников события c Id '%s' исчерпан", event.getId()));
        if (event.getInitiator().equals(user))
            throw new BaseRelationshipException(String.format("Организатору события c Id '%s' нельзя в нем учавствовать", event.getId()));
        if (event.getState().equals(PENDING) || event.getState().equals(CANCELED))
            throw new BaseRelationshipException(String.format("Событие c Id '%s' не опубликовано", event.getId()));

        ParticipationRequest.ParticipationRequestBuilder request = ParticipationRequest.builder();
        request.event(event);
        request.requester(user);
        if (event.getRequestModeration().equals(false) || (event.getParticipantLimit() == 0))
            request.status(RequestStatus.CONFIRMED);

        return request.build();
    }
}
