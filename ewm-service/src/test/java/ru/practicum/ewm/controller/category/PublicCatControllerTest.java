package ru.practicum.ewm.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.service.category.CategoryService;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PublicCatController.class)
class PublicCatControllerTest {

    @MockBean
    private CategoryService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @SneakyThrows
    void findCatById() {
        CategoryDto catDto = CategoryDto.builder().id(1L).build();
        when(service.findCatById(anyLong())).thenReturn(catDto);
        String response = mvc.perform(get("/categories/{catId}", "1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertEquals(mapper.writeValueAsString(catDto), response);
        verify(service).findCatById(1L);
    }

    @Test
    @SneakyThrows
    void findCatById_whenBadPath_returnException() {
        CategoryDto catDto = CategoryDto.builder().id(1L).build();
        when(service.findCatById(anyLong())).thenReturn(catDto);
        mvc.perform(get("/categories/{catId}", "bad"))
                .andExpect(status().is4xxClientError());
         verifyNoInteractions(service);
    }
}