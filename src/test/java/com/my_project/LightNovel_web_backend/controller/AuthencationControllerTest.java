package com.my_project.LightNovel_web_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my_project.LightNovel_web_backend.dto.request.UserRequest;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.enums.Role;
import com.my_project.LightNovel_web_backend.service.Authentication.AuthenticationService;
import com.my_project.LightNovel_web_backend.service.User.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthencationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private UserService userService;

    private UserRequest userRequest;

    private UserResponse userResponse;


    @Test
    public void shouldReturn400WhenEmailInvalid() throws Exception {
        userRequest = UserRequest.builder()
                .email("1234455556")
                .userName("TESTUSER")
                .password("123456")
                .image("abc")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void shouldReturn400WhenUserNameInvalid() throws Exception {
        userRequest = UserRequest.builder()
                .email("example@gmail.com")
                .userName("TEST")
                .password("123456")
                .image("abc")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturn400WhenPasswordInvalid() throws Exception {
        userRequest = UserRequest.builder()
                .email("example@gmail.com")
                .userName("TESTUSER")
                .password("123")
                .image("abc")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldNewUser() throws Exception {
        userRequest = UserRequest.builder()
                .email("example@gmail.com")
                .userName("TESTUSER")
                .image("abc")
                .password("123456")
                .build();
        userResponse = UserResponse.builder()
                .id(1L)
                .userName("TESTUSER")
                .email("example@gmail.com")
                .image("abc")
                .role(Role.USER)
                .build();
        String content = new ObjectMapper().writeValueAsString(userRequest);
        Mockito.when(userService.addUser(ArgumentMatchers.any())).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("userName").value("TESTUSER"))
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("example@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("image").value("abc"))
                .andExpect(MockMvcResultMatchers.jsonPath("role").value("USER"));
    }

}
