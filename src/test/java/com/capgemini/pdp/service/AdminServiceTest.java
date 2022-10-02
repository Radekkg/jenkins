package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.domain.CompetencyEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.dto.*;
import com.capgemini.pdp.repository.CareerPathRepository;
import com.capgemini.pdp.repository.CompetencyRepository;
import com.capgemini.pdp.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private CareerPathRepository careerPathRepository;
    @Autowired
    private CompetencyRepository competencyRepository;

    private static int generator;
    private CareerPathEntity savedCareerPath1;
    private PositionEntity savedPositionForCareerPath11;
    private PositionEntity savedPositionForCareerPath12;
    private CompetencyEntity savedCompetencyForPosition111;
    private CompetencyEntity savedCompetencyForPosition121;

    @BeforeEach
    void setUp() {
        savedCareerPath1 = careerPathRepository.save(createExampleCareerPath());
        savedPositionForCareerPath11 = positionRepository.save(createExamplePosition(savedCareerPath1));
        savedPositionForCareerPath12 = positionRepository.save(createExamplePosition(savedCareerPath1));
        savedCompetencyForPosition111 = competencyRepository.save(createExampleCompetency(savedPositionForCareerPath11));
        savedCompetencyForPosition121 = competencyRepository.save(createExampleCompetency(savedPositionForCareerPath12));
    }

    @Test
    void shouldReturnAllCompetencyByPositionName() {
        //given
        List<PositionEntity> positionEntityList = positionRepository.findAll();
        String positionName = positionEntityList.get(0).getPositionName();

        //when
        List<CompetencyDto> allCompetencyDtoFilteredByPosition = adminService
                .getAllCompetencyDtoFilteredByPosition(positionName);

        //then
        assertFalse(allCompetencyDtoFilteredByPosition.isEmpty());
        allCompetencyDtoFilteredByPosition.stream().forEach(competencyDto -> assertEquals(competencyDto.getPositionName(), positionName));
    }

    @Test
    void shouldBeAbleToAddNewCareerPath() {
        //given
        String newCareerPathName = "NEW_PATH";
        CareerPathDto careerPathDto = new CareerPathDto();
        careerPathDto.setCareerPathName(newCareerPathName);
        //when
        CareerPathDto newCareerPath = adminService.addNewCareerPath(careerPathDto);
        //then
        assertNotNull(newCareerPath.getId());
        Optional<CareerPathEntity> newCareerPathOptional = careerPathRepository.findById(newCareerPath.getId());
        assertTrue(newCareerPathOptional.isPresent());
        assertEquals(newCareerPathOptional.get().getCareerPathName(), newCareerPathName);
    }

    @Test
    void shouldOverrideCareerPathNameWhenArchiving() {
        //given
        String archiveMarker = "unassigned";
        //when
        adminService.archiveCareerPath(savedCareerPath1.getId());
        //then
        assertEquals(savedCareerPath1.getCareerPathName(), archiveMarker);

    }

    @Test
    void shouldOverrideAllPositionNamesOfPositionsBelongingInCareerPathWhenArchivingCareerPath() {
        //given
        String archiveMarker = "unassigned";
        //when
        adminService.archiveCareerPath(savedCareerPath1.getId());
        //then
        List<PositionEntity> allPositionsByCareerPathId = positionRepository.findAllByCareerPathId(savedCareerPath1.getId());
        allPositionsByCareerPathId.forEach(positionEntity -> assertEquals(positionEntity.getPositionName(), archiveMarker));
    }

    @Test
    void shouldBeAbleEditCareerPath() {
        //given
        Long id = savedCareerPath1.getId();
        String name = savedCareerPath1.getCareerPathName();
        String newName = name + "new";
        CareerPathDto careerPathDto = new CareerPathDto();
        careerPathDto.setId(id);
        careerPathDto.setCareerPathName(newName);
        //when
        CareerPathDto updatedCareerPathDto1 = adminService.editCareerPath(careerPathDto);
        //then
        assertEquals(updatedCareerPathDto1.getId(), id);
        assertEquals(updatedCareerPathDto1.getCareerPathName(), newName);
    }

    @Test
    void shouldBeAbleAddNewPositionToCareerPath() {
        //given
        List<PositionEntity> allByCareerPathIdBefore = positionRepository.findAllByCareerPathId(savedCareerPath1.getId());
        PositionDto newPositionDto = new PositionDto();
        newPositionDto.setPositionName("new_position");
        newPositionDto.setCareerPathId(savedCareerPath1.getId());
        //when
        PositionDto positionDto = adminService.addNewPositionToCareerPath(newPositionDto);

        //then
        assertNotNull(positionDto.getId());
        List<PositionEntity> allByCareerPathIdAfter = positionRepository.findAllByCareerPathId(savedCareerPath1.getId());
        assertEquals(allByCareerPathIdAfter.size(), allByCareerPathIdBefore.size() + 1);
    }

    @Test
    void shouldBeAbleEditPosition() {
        //given
        Long id = savedPositionForCareerPath12.getId();
        String positionName = savedPositionForCareerPath12.getPositionName();
        String newPositionName = positionName + "new";
        Long careerPathId = savedPositionForCareerPath12.getCareerPath().getId();
        PositionDto positionDto = new PositionDto();
        positionDto.setId(id);
        positionDto.setPositionName(newPositionName);
        positionDto.setCareerPathId(careerPathId);
        //when
        PositionDto updatedPositionDto = adminService.editPosition(positionDto);

        //then
        assertEquals(updatedPositionDto.getId(), id);
        assertEquals(updatedPositionDto.getPositionName(), newPositionName);
        assertEquals(savedPositionForCareerPath12.getPositionName(), newPositionName);
    }

    @Test
    void shouldBeAbleArchivePosition() {
        //given
        Long id = savedPositionForCareerPath12.getId();
        //when
        adminService.archivePosition(id);
        //then
        assertEquals("unassigned", savedPositionForCareerPath12.getPositionName());
    }

    @Test
    void shouldBeAbleAddNewCompetencyToPosition() {
        //given
        String positionName = savedPositionForCareerPath11.getPositionName();
        CompetencyDto competencyDto = new CompetencyDto();
        competencyDto.setDescription("description");
        competencyDto.setPositionName(positionName);
        //when
        CompetencyDto newCompetencyDto = adminService.addNewCompetencyToPosition(competencyDto);
        //then
        assertNotNull(newCompetencyDto.getId());
        assertEquals(newCompetencyDto.getPositionName(), positionName);
    }

    @Test
    void shouldAddArchiveMarkerToCompetencyDescriptionWhenArchivingCompetency() {
        //given
        String archiveMarker = "(archived)";
        assertThat(savedCompetencyForPosition111.getDescription(), not(containsString(archiveMarker)));
        //when
        adminService.archiveCompetency(savedCompetencyForPosition111.getId());
        //then
        assertThat(savedCompetencyForPosition111.getDescription(), containsString(archiveMarker));
    }

    @Test
    void shouldGetAllUsersFilteredByPositionName() {
        //given
        List<PositionEntity> positionEntityList = positionRepository.findAll();
        PositionEntity positionEntity = positionEntityList.get(0);
        String positionName = positionEntity.getPositionName();
        //when
        List<UserForManagerAndAdminViewDto> allUsersFilteredByPosition = adminService.getAllUsersFilteredByPosition(positionName);

        //then
        allUsersFilteredByPosition.forEach(user -> assertEquals(user.getPosition(), positionName));
    }

    @Test
    void shouldBeAbleAddNewEmployee() {
        //given
        UserDto newUserDto = UserDto.builder().email("email@email.com")
                .firstName("name").lastName("last")
                .roleName("USER").password("123").build();
        //when
        UserDto savedNewUserDto = adminService.addNewEmployee(newUserDto);
        //then
        assertNotNull(savedNewUserDto.getId());

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