package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.annotation.startBeforEnd.ValidStartIsBeforeEnd;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.UpdateEventAdminRequest;
import ru.practicum.ewm.dto.event.enums.State;
import ru.practicum.ewm.service.event.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
public class AdminEventController {
    private final EventService service;

    @GetMapping
    @ValidStartIsBeforeEnd
    public List<EventFullDto> getEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                               @RequestParam(required = false) List<State> states,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now().minusYears(200)}") LocalDateTime rangeStart,
                                               @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDateTime).now().plusYears(200)}") LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "0", required = false) int from,
                                               @RequestParam(defaultValue = "10", required = false) int size) {
        return service.getEventsByAdmin(users, states, categories, rangeStart,
                rangeEnd, PageRequest.of(from / size, size));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable long eventId,
                                           @Valid @RequestBody UpdateEventAdminRequest event) {
        return service.updateEventByAdmin(eventId, event);
    }
}
