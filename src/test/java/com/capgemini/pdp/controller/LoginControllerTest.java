package com.capgemini.pdp.controller;

import com.capgemini.pdp.dto.CredentialsDto;
import com.capgemini.pdp.dto.LoggedInUserDto;
import com.capgemini.pdp.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(loginService))
                .build();
    }

    @Test
    void shouldReturnLoggedInDtoWhenUserExist() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setId(1L);
        credentialsDto.setEmail("email");
        String requestBody = objectMapper.writeValueAsString(credentialsDto);

        LoggedInUserDto user = new LoggedInUserDto();
        user.setId(1L);
        user.setPassword("123");
        user.setRole("USER");
        String userJson = objectMapper.writeValueAsString(user);

        given(loginService.login(any(CredentialsDto.class))).willReturn(user);
        //when
        ResultActions resultActions = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }
}
