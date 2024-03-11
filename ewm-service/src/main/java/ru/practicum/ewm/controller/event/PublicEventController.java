package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.MultiClient;
import ru.practicum.ewm.annotation.startBeforEnd.ValidStartIsBeforeEnd;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.dto.event.enums.SortVariant;
import ru.practicum.ewm.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PublicEventController {

    private final EventService service;
    private final MultiClient multiClient;

    @GetMapping("/events")
    @ValidStartIsBeforeEnd
    public List<EventShortDto> getEvents(@RequestParam(value = "text", required = false)
                                         String text,
                                         @RequestParam(value = "categories", required = false)
                                         List<Long> categories,
                                         @RequestParam(value = "paid", required = false)
                                         Boolean paid,
                                         @RequestParam(required = false,
                                                 defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime rangeStart,
                                         @RequestParam(required = false,
                                                 defaultValue = "#{T(java.time.LocalDateTime).now().plusYears(200)}")
                                         LocalDateTime rangeEnd,
                                         @RequestParam(value = "paid", defaultValue = "false")
                                         Boolean onlyAvailable,
                                         @RequestParam(value = "sort", required = false, defaultValue = "EVENT_DATE") SortVariant sort,
                                         @RequestParam(value = "from", required = false, defaultValue = "0")
                                         Integer from,
                                         @RequestParam(value = "size", required = false, defaultValue = "10")
                                         Integer size,
                                         HttpServletRequest request) {
        multiClient.saveHit(request);
        return service.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, PageRequest.of(from / size, size));
    }

    @Transactional
    @GetMapping("events/{eventId}")
    public EventFullDto getEvent(@PathVariable long eventId,
                                 HttpServletRequest request) {
        multiClient.saveHit(request);
        return service.getEventById(eventId);
    }

}
