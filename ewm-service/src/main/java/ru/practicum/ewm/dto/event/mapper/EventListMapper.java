package ru.practicum.ewm.dto.event.mapper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.ViewStatsDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EventListMapper {
    @Autowired
    private EventMapper mapper;

    public EventListMapper(EventMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * метод преобразует модель в ДТО, добавляя статистику просмотров
     */
    public List<EventShortDto> modelsToDtos(List<Event> events, List<ViewStatsDto> viewStatsDtos) {
        if (events == null) return null;

        // парсим статистику
        Map<Long, Long> hitsMap = new HashMap<>();

        if (viewStatsDtos != null) {
            for (ViewStatsDto viewStatsDto : viewStatsDtos) {
                try {
                    String[] splitUri = viewStatsDto.getUri().split("/");
                    long eventId = Long.parseLong(splitUri[splitUri.length - 1]);
                    hitsMap.put(eventId, viewStatsDto.getHits());
                } catch (Exception e) {
                    log.warn("Ошибка чтения объекта: {}", viewStatsDto.toString());
                }
            }
        }
        // мапим объект
        List<EventShortDto> dtos = new ArrayList<>();
        for (Event event : events) {
            long views = hitsMap.containsKey(event.getId()) ? hitsMap.get(event.getId()) : 0L;
            EventShortDto dto = mapper.modelToShortDto(event, views);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<EventFullDto> modelsToFullDtos(List<Event> events, List<ViewStatsDto> viewStatsDtos) {
        if (events == null) return null;

        // парсим статистику
        Map<Long, Long> hitsMap = new HashMap<>();

        if (viewStatsDtos != null) {
            for (ViewStatsDto viewStatsDto : viewStatsDtos) {
                try {
                    String[] splitUri = viewStatsDto.getUri().split("/");
                    long eventId = Long.parseLong(splitUri[splitUri.length - 1]);
                    hitsMap.put(eventId, viewStatsDto.getHits());
                } catch (Exception e) {
                    log.warn("Ошибка чтения объекта: {}", viewStatsDto.toString());
                }
            }
        }
        // мапим объект
        List<EventFullDto> dtos = new ArrayList<>();
        for (Event event : events) {
            long views = hitsMap.containsKey(event.getId()) ? hitsMap.get(event.getId()) : 0L;
            EventFullDto dto = mapper.modelToDto(event, views);
            dtos.add(dto);
        }
        return dtos;
    }

}
