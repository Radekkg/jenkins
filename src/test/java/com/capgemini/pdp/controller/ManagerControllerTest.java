package com.capgemini.pdp.controller;

import com.capgemini.pdp.dto.UserForManagerAndAdminViewDto;
import com.capgemini.pdp.service.ManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ManagerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ManagerService managerService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ManagerController(managerService))
                .build();
    }

    @Test
    void shouldGetAllCareerPaths() throws Exception {
        //given
        final List<UserForManagerAndAdminViewDto> resultList = new ArrayList<>();
        UserForManagerAndAdminViewDto exampleUser = UserForManagerAndAdminViewDto.builder().id(1L).firstName("name").build();
        resultList.add(exampleUser);
        given(managerService.getUsersByManagerId(1L)).willReturn(resultList);
        //when //then
        this.mockMvc.perform(get("/employee/manager/{managerId}", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("name")));
    }
}