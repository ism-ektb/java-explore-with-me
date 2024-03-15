package ru.practicum.ewm.controller.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.event.EventRequestStatusUpdateResponse;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.location.LocationDto;
import ru.practicum.ewm.dto.request.RequestStatus;
import ru.practicum.ewm.exception.exception.NoFoundObjectException;
import ru.practicum.ewm.service.event.EventService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PrivateEventController.class)
class PrivateEventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EventService service;

    @Test
    @SneakyThrows
    void saveEvent_whenDefaultValuePaid_thenReturnOk() {
        NewEventDto newEventDto = NewEventDto.builder()
                .annotation("Двадцать__один_знак!!!")
                .category(1L)
                .description("Двадцать__один_знак!!!")
                .eventDate(LocalDateTime.of(2025, 01, 01, 00, 00))
                .location(LocationDto.builder().lat(60F).lon(60F)
                        .build())
                //      .paid(false).participantLimit(0).requestModeration(true)
                .title("test").build();
        NewEventDto newEventDto2 = NewEventDto.builder()
                .annotation("Двадцать__один_знак!!!")
                .category(1L)
                .description("Двадцать__один_знак!!!")
                .eventDate(LocalDateTime.of(2025, 01, 01, 00, 00))
                .location(LocationDto.builder().lat(60F).lon(60F)
                        .build())
                .paid(false).participantLimit(0).requestModeration(true)
                .title("test").build();
        EventFullDto eventFullDto = EventFullDto.builder().build();
        when(service.saveEvent(anyLong(), any())).thenReturn(eventFullDto);
        String response = mvc.perform(post("/users/{userId}/events", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newEventDto)))
                .andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertEquals(mapper.writeValueAsString(eventFullDto), response);
        verify(service).saveEvent(1L, newEventDto2);
    }

    @Test
    @SneakyThrows
    void saveEvent() {
        NewEventDto newEventDto = NewEventDto.builder()
                .annotation("Двадцать__один_знак!!!")
                .category(1L)
                .description("Двадцать__один_знак!!!")
                .eventDate(LocalDateTime.of(2025, 01, 01, 00, 00))
                .location(LocationDto.builder().lat(60F).lon(60F)
                        .build())
                .paid(true)
                //      .participantLimit(0).requestModeration(true)
                .title("test").build();
        NewEventDto newEventDto2 = NewEventDto.builder()
                .annotation("Двадцать__один_знак!!!")
                .category(1L)
                .description("Двадцать__один_знак!!!")
                .eventDate(LocalDateTime.of(2025, 01, 01, 00, 00))
                .location(LocationDto.builder().lat(60F).lon(60F)
                        .build())
                .paid(true).participantLimit(0).requestModeration(true)
                .title("test").build();
        EventFullDto eventFullDto = EventFullDto.builder().build();
        when(service.saveEvent(anyLong(), any())).thenReturn(eventFullDto);
        String response = mvc.perform(post("/users/{userId}/events", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newEventDto)))
                .andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertEquals(mapper.writeValueAsString(eventFullDto), response);
        verify(service).saveEvent(1L, newEventDto2);
    }

    @Test
    @SneakyThrows
    void saveEvent_whenTimeIsNotValid_ThenReturnException() {
        NewEventDto newEventDto = NewEventDto.builder()
                .annotation("Двадцать__один_знак!!")
                .category(1L)
                .description("Двадцать__один_знак!!")
                .eventDate(LocalDateTime.now().plusHours(1))
                .location(LocationDto.builder().lat(60F).lon(60F)
                        .build())
                .paid(true)
                //      .participantLimit(0).requestModeration(true)
                .title("test").build();
        mvc.perform(post("/users/{userId}/events", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(newEventDto)))
                .andExpect(status().is4xxClientError());
        verifyNoInteractions(service);
    }

    @SneakyThrows
    @Test
    void getEvent() {
        when(service.getEventByIdByUser(anyLong(), anyLong())).thenThrow(new NoFoundObjectException("1"));
        mvc.perform(get("/users/{userId}/events/{eventId}", "1", "2"))

                .andExpect(status().is4xxClientError());
    }

    @Test
    @SneakyThrows
    void conformRequestsByUser() {
        EventRequestStatusUpdateRequest req = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1L))
                .status(RequestStatus.REJECTED)
                .build();
        EventRequestStatusUpdateResponse resp = EventRequestStatusUpdateResponse.builder().build();
        when(service.conformRequestsByUser(anyLong(), anyLong(), any()))
                .thenReturn(resp);
        mvc.perform(patch("/users/{userId}/events/{eventId}/requests", "1", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().is2xxSuccessful());
        verify(service).conformRequestsByUser(1L, 2L, req);
    }

    @Test
    @SneakyThrows
    void conformRequestsByUser_sendInBodyBadStatus_ReturnException() {
        EventRequestStatusUpdateRequest req = EventRequestStatusUpdateRequest.builder()
                .requestIds(List.of(1L))
                .status(RequestStatus.PENDING)
                .build();
        EventRequestStatusUpdateResponse resp = EventRequestStatusUpdateResponse.builder().build();
        when(service.conformRequestsByUser(anyLong(), anyLong(), any()))
                .thenReturn(resp);
        mvc.perform(patch("/users/{userId}/events/{eventId}/requests", "1", "2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().is4xxClientError());
        verifyNoInteractions(service);
    }
}