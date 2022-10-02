package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.domain.CompetencyEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.CompetencyDto;
import com.capgemini.pdp.dto.CompetencyForUserDto;
import com.capgemini.pdp.mapper.CompetencyMapper;
import com.capgemini.pdp.repository.CareerPathRepository;
import com.capgemini.pdp.repository.CompetencyRepository;
import com.capgemini.pdp.repository.PositionRepository;
import com.capgemini.pdp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CompetencyServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CareerPathRepository careerPathRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private CompetencyRepository competencyRepository;
    @Autowired
    private CompetencyService competencyService;

    private static int generator;
    private CareerPathEntity savedCareerPath1;
    private UserEntity userAdded1;
    private PositionEntity positionAdded1;
    private CompetencyEntity competencyAdded1;
    private CompetencyEntity competencyAdded2;

    @BeforeEach
    void setUp() {
        savedCareerPath1 = careerPathRepository.save(createExampleCareerPath());
        positionAdded1 = positionRepository.save(createExamplePosition(savedCareerPath1));
        competencyAdded1 = competencyRepository.save(createExampleCompetency(positionAdded1));
        competencyAdded2 = competencyRepository.save(createExampleCompetency(positionAdded1));
    }

    @Test
    void shouldGetCompetenciesAvailableForUserAndUsersPosition() {
        //given
        UserEntity userEntity = createExampleUser();
        userEntity.setRoleName(UserRole.USER);
        userEntity.setPosition(positionAdded1);
        userAdded1 = userRepository.save(userEntity);
        //when
        List<CompetencyForUserDto> competencyForUserDtos = competencyService.getCompetenciesAvailableForUser(userAdded1.getId());
        //then
        assertEquals("TEST", competencyForUserDtos.get(0).getDescription());
        assertEquals("TEST", competencyForUserDtos.get(1).getDescription());
    }

    @Test
    void shouldThrowWhenUserIdInvalid() {
        //then
        assertThrows(NoSuchElementException.class, () -> competencyService.getCompetenciesAvailableForUser(-1L));
    }

    @Test
    void shouldAddNewCompetency() {
        //given
        CompetencyDto competencyDto = CompetencyMapper.mapToDto(createExampleCompetency(positionAdded1));
        //when
        CompetencyDto added = competencyService.addCompetency(competencyDto);
        //then
        assertNotNull(added.getId());
        assertEquals(competencyDto.getDescription(), added.getDescription());
        assertEquals(competencyDto.getPositionName(), added.getPositionName());
    }

    @Test
    void shouldNotAddCompetencyWithoutPositionName() {
        //given
        CompetencyDto competencyDto = new CompetencyDto();
        competencyDto.setPositionName(null);
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () -> competencyService.addCompetency(competencyDto));
    }

    @Test
    void shouldGetCompetencyById() {
        //given
        Long id = competencyAdded1.getId();
        //when
        CompetencyDto foundCompetency = competencyService.getCompetencyById(id);
        //then
        assertNotNull(foundCompetency);
        assertEquals(competencyAdded1.getId(), foundCompetency.getId());
        assertEquals(competencyAdded1.getPosition().getPositionName(), foundCompetency.getPositionName());
    }

    @Test
    void shouldThrowExceptionWhenCompetencyWithProvidedIdDoesNotExist() {
        //given
        Long id = -1L;
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> competencyService.getCompetencyById(id));
    }

    @Test
    void shouldUpdateCompetency() {
        //given
        String oldDescription = competencyAdded2.getDescription();
        CompetencyDto competencyUpdate = new CompetencyDto();
        competencyUpdate.setId(competencyAdded2.getId());
        competencyUpdate.setPositionName(competencyAdded2.getPosition().getPositionName());
        competencyUpdate.setDescription("new description");
        //when
        CompetencyDto updated = competencyService.updateCompetency(competencyUpdate);
        //then
        assertNotEquals(oldDescription, updated.getDescription());
        assertEquals(competencyUpdate.getDescription(), updated.getDescription());
        assertEquals(competencyAdded2.getId(), updated.getId());
    }

    @Test
    void shouldGetAllCareerPaths() {
        //given
        //when
        List<CompetencyDto> foundCompetency = competencyService.getAllCompetencies();
        //then
        assertNotNull(foundCompetency);
    }

    @Test
    void shouldGetCompetenciesByPositionName() {
        //given
        String positionName = "expected name";

        CareerPathEntity careerPathEntity = createExampleCareerPath();
        CareerPathEntity savedCareerPath = careerPathRepository.save(careerPathEntity);
        PositionEntity positionEntity = createExamplePosition(savedCareerPath);
        positionEntity.setPositionName(positionName);
        positionRepository.save(positionEntity);

        CompetencyDto competency = new CompetencyDto();
        competency.setDescription("description");
        competency.setPositionName(positionName);

        competencyService.addCompetency(competency);
        competencyService.addCompetency(competency);
        competencyService.addCompetency(competency);

        //when
        List<CompetencyDto> foundCompetencies = competencyService.getAllByPositionName(positionName);

        //then
        assertNotNull(foundCompetencies);
        assertEquals(3, foundCompetencies.size());
        foundCompetencies.forEach(found -> assertEquals(positionName, found.getPositionName()));
    }

    @Test
    void shouldThrowExceptionWhenCompetencyWithProvidedPositionNameDoesNotExist() {
        //given
        String positionName = "xxx";
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> competencyService.getAllByPositionName(positionName));
    }

    @Test
    void shouldArchiveAllCompetenciesByPositionId() {
        //given
        CareerPathEntity careerPathEntity = createExampleCareerPath();
        CareerPathEntity savedCareerPath = careerPathRepository.save(careerPathEntity);
        PositionEntity positionEntity = createExamplePosition(savedCareerPath);
        PositionEntity savedPosition = positionRepository.save(positionEntity);
        CompetencyEntity competency = createExampleCompetency(savedPosition);

        competencyRepository.save(competency);
        competencyRepository.save(competency);
        competencyRepository.save(competency);

        Long id = savedPosition.getId();
        //when
        competencyService.archiveAllCompetenciesByPositionId(id);
        List<CompetencyDto> competenciesWithoutArchivedPosition = competencyService.getAllCompetencies();
        //then
        assertEquals(0, competenciesWithoutArchivedPosition.stream()
                .filter(e -> e.getPositionName().equals("unassigned"))
                .count());
    }

    @Test
    void shouldArchiveCareerPath() {
        //given
        Long id = competencyAdded1.getId();
        //when
        competencyService.archiveCompetency(id);
        //then
        assertTrue(competencyAdded1.getDescription().endsWith("(archived)"));
    }

    private CompetencyEntity createExampleCompetency(PositionEntity positionEntity) {
        CompetencyEntity competencyEntity = new CompetencyEntity();
        competencyEntity.setDescription("TEST");
        competencyEntity.setPosition(positionEntity);
        return competencyEntity;
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

    private UserEntity createExampleUser() {
        UserEntity user = new UserEntity();
        generator++;
        user.setFirstName("name" + generator);
        user.setLastName("lastname" + generator);
        user.setPassword("password" + generator);
        user.setEmail(generator + "employee@gmail.com");
        return user;
    }

}