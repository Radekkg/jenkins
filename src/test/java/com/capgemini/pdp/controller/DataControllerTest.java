package com.capgemini.pdp.controller;

import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.CareerPathDto;
import com.capgemini.pdp.dto.CompetencyDto;
import com.capgemini.pdp.dto.PositionDto;
import com.capgemini.pdp.dto.TypeDto;
import com.capgemini.pdp.service.CareerPathService;
import com.capgemini.pdp.service.CompetencyService;
import com.capgemini.pdp.service.PositionService;
import com.capgemini.pdp.service.TypeService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DataControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CareerPathService careerPathService;
    @Mock
    private CompetencyService competencyService;
    @Mock
    private PositionService positionService;
    @Mock
    private TypeService typeService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DataController(careerPathService, competencyService, positionService, typeService))
                .build();
    }

    @Test
    void shouldGetAllCareerPaths() throws Exception {
        //given
        final List<CareerPathDto> resultList = new ArrayList<>();
        CareerPathDto exampleCareerPathDto = new CareerPathDto();
        exampleCareerPathDto.setId(1L);
        exampleCareerPathDto.setCareerPathName("name");
        resultList.add(exampleCareerPathDto);
        given(careerPathService.getAllCareerPaths()).willReturn(resultList);
        //when //then
        this.mockMvc.perform(get("/data/careerPath"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].careerPathName", is("name")));
    }

    @Test
    void shouldGetAllPositions() throws Exception {
        //given
        final List<PositionDto> resultList = new ArrayList<>();
        PositionDto examplePositionDto = new PositionDto();
        examplePositionDto.setId(1L);
        examplePositionDto.setPositionName("name");
        resultList.add(examplePositionDto);
        given(positionService.getAllPositions()).willReturn(resultList);
        //when //then
        this.mockMvc.perform(get("/data/position"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].positionName", is("name")));
    }

    @Test
    void shouldGetCompetencies() throws Exception {
        //given
        final List<CompetencyDto> resultList = new ArrayList<>();
        CompetencyDto exampleCompetencyDto = new CompetencyDto();
        exampleCompetencyDto.setId(1L);
        exampleCompetencyDto.setDescription("description");
        resultList.add(exampleCompetencyDto);
        given(competencyService.getAllCompetencies()).willReturn(resultList);
        //when //then
        this.mockMvc.perform(get("/data/competency"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("description")));
    }

    @Test
    void shouldGetAllRoles() throws Exception {
        //given
        List<String> resultList = Stream.of(UserRole.values())
                .map(UserRole::name)
                .collect(Collectors.toList());
        //when //then
        this.mockMvc.perform(get("/data/role"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]", is(resultList.get(0))));
    }

    @Test
    void shouldGetAllTypes() throws Exception {
        //given
        final List<TypeDto> resultList = new ArrayList<>();
        TypeDto exampleCompetencyDto = new TypeDto();
        exampleCompetencyDto.setId(1L);
        exampleCompetencyDto.setDescription("description");
        resultList.add(exampleCompetencyDto);
        given(typeService.getAllTypes()).willReturn(resultList);
        //when //then
        this.mockMvc.perform(get("/data/type"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("description")));
    }

}