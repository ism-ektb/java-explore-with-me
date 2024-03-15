package ru.practicum.ewm.controller.event;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.practicum.MultiClient;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.service.comment.CommentService;
import ru.practicum.ewm.service.event.EventService;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PublicEventController.class)
class PublicEventControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService service;
    @MockBean
    private MultiClient multiClient;
    @MockBean
    private CommentService commentService;

    @Test
    @SneakyThrows
    void getEvents() {
        EventShortDto eventShortDto = EventShortDto.builder().id(1L).build();
        when(service.getAllEvents(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(List.of(eventShortDto));
        mvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}