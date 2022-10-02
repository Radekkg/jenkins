package com.capgemini.pdp.controller;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.domain.CompetencyEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.CareerPathDto;
import com.capgemini.pdp.dto.CompetencyDto;
import com.capgemini.pdp.dto.PositionDto;
import com.capgemini.pdp.dto.UserDto;
import com.capgemini.pdp.mapper.CareerPathMapper;
import com.capgemini.pdp.mapper.CompetencyMapper;
import com.capgemini.pdp.mapper.PositionMapper;
import com.capgemini.pdp.mapper.UserMapper;
import com.capgemini.pdp.service.impl.AdminServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminServiceImpl adminService;

    private final List<CompetencyDto> competencies = new ArrayList<>();
    private static int generator;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new AdminController(adminService))
                .build();

        CareerPathEntity careerPath = createExampleCareerPath();
        PositionEntity position = createExamplePosition(careerPath);
        CompetencyDto competency1 = CompetencyMapper.mapToDto(createExampleCompetency(position));
        competency1.setPositionName("position");
        CompetencyDto competency2 = CompetencyMapper.mapToDto(createExampleCompetency(position));
        competency1.setPositionName("position");
        competencies.add(competency1);
        competencies.add(competency2);
    }

    @Test
    void shouldReturnCompetenciesFilteredByPosition() {
        //given
        when(adminService.getAllCompetencyDtoFilteredByPosition(any()))
                .thenReturn(competencies);
        //when
        ResponseEntity<List<CompetencyDto>> response = adminController.getAllCareerPaths("position1");
        //then
        assertNotNull(response);
        assertEquals(competencies.get(0), Objects.requireNonNull(response.getBody()).get(0));
        assertEquals(competencies.size(), response.getBody().size());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void shouldReturnNullWhenProvidedPositionIsNotFound() {
        //given
        when(adminService.getAllCompetencyDtoFilteredByPosition(any()))
                .thenReturn(null);
        //when
        ResponseEntity<List<CompetencyDto>> response = adminController.getAllCareerPaths("position");
        //then
        assertNull(response.getBody());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void shouldAddNewCareerPath() {
        //given
        CareerPathDto careerPath = CareerPathMapper.mapToDto(createExampleCareerPath());

        when(adminService.addNewCareerPath(any()))
                .thenReturn(careerPath);
        //when
        ResponseEntity<CareerPathDto> response = adminController.addCareerPath(careerPath);
        //then
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getCareerPathName(), careerPath.getCareerPathName());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void shouldUpdateCareerPath() {
        //given
        CareerPathDto newCareerPath = CareerPathMapper.mapToDto(createExampleCareerPath());
        when(adminService.editCareerPath(any())).thenReturn(newCareerPath);
        //when
        ResponseEntity<CareerPathDto> response = adminController.updateCareerPath(newCareerPath);
        //then
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getCareerPathName(), newCareerPath.getCareerPathName());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void shouldReturnCompetencies() throws Exception {
        //given
        given(adminService.getAllCompetencyDtoFilteredByPosition(any())).willReturn(competencies);
        //when //then
        this.mockMvc.perform(get("/admin/competency/position"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].positionName", is("position")));
    }

    @Test
    void shouldRemoveCareerPath() throws Exception {
        //given
        //when
        adminService.archiveCareerPath(1L);
        //then
        verify(adminService, times(1)).archiveCareerPath(1L);
        this.mockMvc.perform(delete("/admin/careerPath/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldAddNewPosition() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();

        CareerPathEntity careerPath = createExampleCareerPath();
        PositionDto position = PositionMapper.mapToDto(createExamplePosition(careerPath));

        String positionJson = objectMapper.writeValueAsString(position);

        given(adminService.addNewPositionToCareerPath(any())).willReturn(position);

        //when
        //then
        this.mockMvc.perform(post("/admin/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(positionJson))
                .andExpect(content().json(positionJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePosition() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();

        CareerPathEntity careerPath = createExampleCareerPath();
        PositionDto positionUpdate = PositionMapper.mapToDto(createExamplePosition(careerPath));

        String positionUpdateJson = objectMapper.writeValueAsString(positionUpdate);

        given(adminService.editPosition(any())).willReturn(positionUpdate);

        //when
        //then
        this.mockMvc.perform(put("/admin/position/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(positionUpdateJson))
                .andExpect(content().json(positionUpdateJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRemovePosition() throws Exception {
        //given
        //when
        adminService.archivePosition(1L);
        //then
        verify(adminService, times(1)).archivePosition(1L);
        this.mockMvc.perform(delete("/admin/position/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldAddNewCompetency() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();

        CareerPathEntity careerPath = createExampleCareerPath();
        PositionEntity position = createExamplePosition(careerPath);
        CompetencyDto competency = CompetencyMapper.mapToDto(createExampleCompetency(position));

        String competencyJson = objectMapper.writeValueAsString(competency);

        given(adminService.addNewCompetencyToPosition(any())).willReturn(competency);

        //when
        //then
        this.mockMvc.perform(post("/admin/competency")
                .contentType(MediaType.APPLICATION_JSON)
                .content(competencyJson))
                .andExpect(content().json(competencyJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCompetency() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();

        CareerPathEntity careerPath = createExampleCareerPath();
        PositionEntity position = createExamplePosition(careerPath);
        CompetencyDto competency = CompetencyMapper.mapToDto(createExampleCompetency(position));

        String competencyJson = objectMapper.writeValueAsString(competency);

        given(adminService.editCompetency(any())).willReturn(competency);

        //when
        //then
        this.mockMvc.perform(put("/admin/competency/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(competencyJson))
                .andExpect(content().json(competencyJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRemoveCompetency() throws Exception {
        //given
        //when
        adminService.archiveCompetency(1L);
        //then
        verify(adminService, times(1)).archiveCompetency(1L);
        this.mockMvc.perform(delete("/admin/competency/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    void shouldAddNewEmployee() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        UserEntity userEntity = createExampleUser();
        userEntity.setRoleName(UserRole.USER);
        UserDto employee = UserMapper.mapToDto(userEntity);

        String employeeJson = objectMapper.writeValueAsString(employee);

        given(adminService.addNewEmployee(any())).willReturn(employee);

        //when
        //then
        this.mockMvc.perform(post("/admin/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(content().json(employeeJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();

        UserEntity userEntity = createExampleUser();
        userEntity.setRoleName(UserRole.MANAGER);
        UserDto employee = UserMapper.mapToDto(userEntity);

        String employeeJson = objectMapper.writeValueAsString(employee);

        given(adminService.editEmployee(any())).willReturn(employee);

        //when
        //then
        this.mockMvc.perform(put("/admin/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(content().json(employeeJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateEmployeeRole() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();

        UserEntity userEntity = createExampleUser();
        userEntity.setRoleName(UserRole.MANAGER);
        UserDto employee = UserMapper.mapToDto(userEntity);

        String employeeJson = objectMapper.writeValueAsString(employee);

        given(adminService.changeEmployeeRole(any(), any())).willReturn(employee);

        //when
        //then
        this.mockMvc.perform(post("/admin/employee/1/manager")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(content().json(employeeJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRemoveEmployee() throws Exception {
        //given
        //when
        adminService.deleteEmployee(1L);
        //then
        verify(adminService, times(1)).deleteEmployee(1L);
        this.mockMvc.perform(delete("/admin/employee/1"))
                .andExpect(status().isAccepted());
    }

    private UserEntity createExampleUser() {
        UserEntity user = new UserEntity();
        generator++;
        user.setFirstName("name" + generator);
        user.setLastName("lastname" + generator);
        user.setPassword("password" + generator);
        user.setEmail(generator + "employee@gmail.com");
        return user;
    }

    private CareerPathEntity createExampleCareerPath() {
        CareerPathEntity careerPathEntity = new CareerPathEntity();
        careerPathEntity.setCareerPathName("name" + generator++);
        return careerPathEntity;
    }

    private PositionEntity createExamplePosition(CareerPathEntity careerPathAdded) {
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setPositionName("name" + generator++);
        positionEntity.setCareerPath(careerPathAdded);
        return positionEntity;
    }

    private CompetencyEntity createExampleCompetency(PositionEntity positionAdded) {
        CompetencyEntity competencyEntity = new CompetencyEntity();
        competencyEntity.setDescription("description" + generator++);
        competencyEntity.setPosition(positionAdded);
        return competencyEntity;
    }
}