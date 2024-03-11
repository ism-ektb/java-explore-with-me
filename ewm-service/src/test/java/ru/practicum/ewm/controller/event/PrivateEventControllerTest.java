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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.NewEventDto;
import ru.practicum.ewm.dto.location.LocationDto;
import ru.practicum.ewm.exception.exception.NoFoundObjectException;
import ru.practicum.ewm.service.event.EventService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PrivateEventController.class)
class PrivateEventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EventService service;

//    @MockBean
//    MultiClient multiClient;

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
        String response = mvc.perform(MockMvcRequestBuilders.post("/users/{userId}/events", "1")
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
        String response = mvc.perform(MockMvcRequestBuilders.post("/users/{userId}/events", "1")
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
        mvc.perform(MockMvcRequestBuilders.post("/users/{userId}/events", "1")
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
        mvc.perform(MockMvcRequestBuilders.get("/users/{userId}/events/{eventId}", "1", "2"))

                .andExpect(status().is4xxClientError());
    }
}