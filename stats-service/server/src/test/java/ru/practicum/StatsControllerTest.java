package ru.practicum;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
class StatsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatsService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @SneakyThrows
    void getStats() {
        ViewStatsDto viewStatsDto = ViewStatsDto.builder().app("ewm-main-service").build();
        when(service.getStats(any(), any(), any(), anyBoolean()))
                .thenReturn(List.of(viewStatsDto));
        String response = mvc.perform(get("/stats")
                        .param("start", "2020-05-05 00:00:00")
                        .param("end", "2035-05-05 00:00:00")
                        .param("unique", "true"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals(mapper.writeValueAsString(List.of(viewStatsDto)), response);
        verify(service).getStats(LocalDateTime.of(2020, 05, 05, 00, 00, 00),
                LocalDateTime.of(2035, 05, 05, 00, 00, 00),
                null, true);
    }

    @SneakyThrows
    @Test
    void getStats_whenBadTypeOfTime_thenReturnError() {
        ViewStatsDto viewStatsDto = ViewStatsDto.builder().app("ewm-main-service").build();
        when(service.getStats(any(), any(), any(), anyBoolean()))
                .thenReturn(List.of(viewStatsDto));
        mvc.perform(get("/stats")
                        .param("start", "2020-05-05T00:00:00")
                        .param("end", "2035-05-05 00:00:00"))
                .andExpect(status().is4xxClientError());
        verifyNoInteractions(service);
    }

    @SneakyThrows
    @Test
    void getStats_whenBadTypeOfBoolean_thenReturnError() {
        ViewStatsDto viewStatsDto = ViewStatsDto.builder().app("ewm-main-service").build();
        when(service.getStats(any(), any(), any(), anyBoolean()))
                .thenReturn(List.of(viewStatsDto));
        mvc.perform(get("/stats")
                        .param("start", java.net.URLEncoder.encode("2020-05-05 00:00:00", "UTF-8"))
                        .param("end", "2035-05-05 00:00:00")
                        .param("unique", "bad"))
                .andExpect(status().is4xxClientError());
        verifyNoInteractions(service);
    }

    @Test
    @SneakyThrows
    void save() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 05, 05, 00, 00, 00);

        EndPointHitDto endPointHitDto = EndPointHitDto.builder()
                .id(1L)
                .app("user1")
                .uri("useruser.com")
                .ip("100.100.100.100")
                .timestamp(dateTime).build();
        when(service.save(any())).thenReturn(endPointHitDto);
        String response = mvc.perform(post("/hit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        //.content(mapper.writeValueAsString(endPointHitDto)))
                        .content("{\"id\":1,\"app\":\"user1\",\"uri\":\"useruser.com\",\"ip\":\"100.100.100.100\",\"timestamp\":\"2020-05-05 00:00:00\"}"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals(mapper.writeValueAsString(endPointHitDto), response);
        verify(service).save(endPointHitDto);
    }
}