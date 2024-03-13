package ru.practicum.ewm.dto.event.patcher;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.event.UpdateEventAdminRequest;
import ru.practicum.ewm.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.dto.location.mapper.LocationMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;

import static ru.practicum.ewm.dto.event.enums.State.*;
import static ru.practicum.ewm.dto.event.enums.StateAdminAction.PUBLISH_EVENT;
import static ru.practicum.ewm.dto.event.enums.StateUserAction.SEND_TO_REVIEW;

@RequiredArgsConstructor
@Component
public class EventPatcher {

    @Autowired
    private final LocationMapper locationMapper;

    public Event adminPatch(Event event, UpdateEventAdminRequest patch) {


        Event.EventBuilder newEvent = Event.builder();
        newEvent.id(event.getId());
        newEvent.annotation(patch.getAnnotation() != null ? patch.getAnnotation() : event.getAnnotation());
        newEvent.category(patch.getCategory() != null ? Category.builder()
                .id(patch.getCategory()).build() : event.getCategory());
        newEvent.createdOn(event.getCreatedOn());
        newEvent.description(patch.getDescription() != null ? patch.getDescription() : event.getDescription());
        newEvent.eventDate(patch.getEventDate() != null ? patch.getEventDate() : event.getEventDate());
        newEvent.initiator(event.getInitiator());
        newEvent.location(patch.getLocation() != null ?
                locationMapper.dtoToModel(patch.getLocation()) : event.getLocation());
        newEvent.paid(patch.getPaid() != null ? patch.getPaid() : event.getPaid());
        newEvent.participantLimit(patch.getParticipantLimit() != null ?
                patch.getParticipantLimit() : event.getParticipantLimit());

        newEvent.requestModeration(patch.getRequestModeration() != null
                ? patch.getRequestModeration() : event.getRequestModeration());
        if (patch.getStateAction() == null) {
            newEvent.state(event.getState());
            newEvent.publishedOn(event.getPublishedOn());
        } else if (patch.getStateAction().equals(PUBLISH_EVENT)) {
            newEvent.state(PUBLISHED);
            newEvent.publishedOn(LocalDateTime.now());
        } else {
            newEvent.state(CANCELED);
            newEvent.publishedOn(LocalDateTime.now());
        }
        newEvent.title(patch.getTitle() != null ? patch.getTitle() : event.getTitle());

        return newEvent.build();
    }

    public Event userPatch(Event event, UpdateEventUserRequest patch) {


        Event.EventBuilder newEvent = Event.builder();
        newEvent.id(event.getId());
        newEvent.annotation(patch.getAnnotation() != null ? patch.getAnnotation() : event.getAnnotation());
        newEvent.category(patch.getCategory() != null ? Category.builder()
                .id(patch.getCategory()).build() : event.getCategory());
        newEvent.createdOn(event.getCreatedOn());
        newEvent.description(patch.getDescription() != null ? patch.getDescription() : event.getDescription());
        newEvent.eventDate(patch.getEventDate() != null ? patch.getEventDate() : event.getEventDate());
        newEvent.initiator(event.getInitiator());
        newEvent.location(patch.getLocation() != null ?
                locationMapper.dtoToModel(patch.getLocation()) : event.getLocation());
        newEvent.paid(patch.getPaid() != null ? patch.getPaid() : event.getPaid());
        newEvent.participantLimit(patch.getParticipantLimit() != null ?
                patch.getParticipantLimit() : event.getParticipantLimit());

        newEvent.requestModeration(patch.getRequestModeration() != null
                ? patch.getRequestModeration() : event.getRequestModeration());
        if (patch.getStateAction() == null) {
            newEvent.state(event.getState());
            newEvent.publishedOn(event.getPublishedOn());
        } else if (patch.getStateAction().equals(SEND_TO_REVIEW)) {
            newEvent.state(PENDING);
            newEvent.publishedOn(LocalDateTime.now());
        } else {
            newEvent.state(CANCELED);
            newEvent.publishedOn(LocalDateTime.now());
        }
        newEvent.title(patch.getTitle() != null ? patch.getTitle() : event.getTitle());

        return newEvent.build();
    }

}
