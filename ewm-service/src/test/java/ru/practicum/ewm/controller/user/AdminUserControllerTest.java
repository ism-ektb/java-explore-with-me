package ru.practicum.ewm.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.service.user.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AdminUserController.class)
class AdminUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService service;

    @Test
    @SneakyThrows
    void saveUser_whenBadEmail_thenReturnError() {
        UserDto userDto = UserDto.builder().email("qq.q").name("Петя").build();
        when(service.saveUser(any())).thenReturn(userDto);
        mvc.perform(post("/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        verifyNoInteractions(service);

    }

    @Test
    @SneakyThrows
    void findUsers() {
        UserDto userDto = UserDto.builder().email("q@q.q").name("Петя").build();
        when(service.findUser(anySet(), any())).thenReturn(List.of(userDto));
        String response = mvc.perform(get("/admin/users")
                        .param("ids", "1,2"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertEquals(mapper.writeValueAsString(List.of(userDto)), response);
        verify(service).findUser(Set.of(1L, 2L), PageRequest.of(0, 10));
    }

    @Test
    @SneakyThrows
    void findUsers_whenFirstParamBad_returnException() {
        UserDto userDto = UserDto.builder().email("q@q.q").name("Петя").build();
        when(service.findUser(anySet(), any())).thenReturn(List.of(userDto));
        mvc.perform(get("/admin/users")
                        .param("ids", "1,bad"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        verifyNoInteractions(service);
    }

    @Test
    @SneakyThrows
    void findUsers_whenSecondParamBad_returnException() {
        UserDto userDto = UserDto.builder().email("q@q.q").name("Петя").build();
        when(service.findUser(anySet(), any())).thenReturn(List.of(userDto));
        mvc.perform(get("/admin/users")
                        .param("ids", "1")
                        .param("from", "-10"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        verifyNoInteractions(service);
    }

    @Test
    void deleteUser() {
    }
}