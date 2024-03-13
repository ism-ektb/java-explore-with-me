package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Slf4j
public class PrivateEventController {
    private final EventService service;

    @GetMapping
    public List<EventShortDto> getEventsByUser(@Positive @PathVariable long userId,
                                               @PositiveOrZero
                                               @RequestParam(defaultValue = "0", required = false) int from,
                                               @Positive
                                               @RequestParam(defaultValue = "10", required = false) int size) {
        return service.getEventsByUser(userId, PageRequest.of(from / size, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto saveEvent(@Positive @PathVariable long userId,
                                  @Valid @RequestBody NewEventDto event) {
        return service.saveEvent(userId, event);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdByUser(@Positive @PathVariable long userId,
                                           @Positive @PathVariable long eventId,
                                           HttpServletRequest request) {
        return service.getEventByIdByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByUser(@Positive @PathVariable long userId,
                                          @Positive @PathVariable long eventId,
                                          @Valid @RequestBody UpdateEventUserRequest event) {
        return service.patchEventByUser(userId, eventId, event);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByEventIdByUser(@Positive @PathVariable long userId,
                                                                    @Positive @PathVariable long eventId) {
        return service.getRequestsByEventIdByUser(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResponse conformRequestsByUser(@PathVariable Long userId,
                                                                  @PathVariable Long eventId,
                                                                  @Valid @RequestBody
                                                                  EventRequestStatusUpdateRequest updateRequest) {
        return service.conformRequestsByUser(userId, eventId, updateRequest);
    }


}
