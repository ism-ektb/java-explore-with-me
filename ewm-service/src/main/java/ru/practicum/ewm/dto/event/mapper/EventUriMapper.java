package ru.practicum.ewm.dto.event.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.model.Event;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventUriMapper {

    public List<String> modelsToUris(List<Event> eventList) {
        if (eventList == null) return null;
        if (eventList.isEmpty()) return new ArrayList<>();
        List<String> uris = new ArrayList<>();
        for (Event event : eventList) {
            uris.add("/events/" + event.getId());
        }
        return uris;

    }
}
