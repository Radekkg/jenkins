package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.dto.PositionDto;
import com.capgemini.pdp.mapper.PositionMapper;
import com.capgemini.pdp.repository.CareerPathRepository;
import com.capgemini.pdp.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PositionServiceTest {

    @Autowired
    private PositionService positionService;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private CareerPathRepository careerPathRepository;

    private static int generator;
    private CareerPathEntity careerPathEntity;
    private PositionEntity addedPosition;

    @BeforeEach
    void setUp() {
        careerPathEntity = careerPathRepository.save(createExampleCareerPath());
        addedPosition = positionRepository.save(createExamplePosition(careerPathEntity));
    }

    @Test
    void shouldAddNewPosition() {
        //given
        PositionDto positionDto = PositionMapper.mapToDto(createExamplePosition(careerPathEntity));
        //when
        PositionDto added = positionService.addNewPositionToCareerPath(positionDto);
        //then
        assertNotNull(added.getId());
        assertEquals(positionDto.getCareerPathId(), added.getCareerPathId());
        assertEquals(positionDto.getPositionName(), added.getPositionName());
    }

    @Test
    void shouldNotAddPositionWithNotValidCareerPathId() {
        //given
        PositionDto positionDto = new PositionDto();
        positionDto.setPositionName("position");
        positionDto.setCareerPathId(-1L);
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> positionService.addNewPositionToCareerPath(positionDto));
    }

    @Test
    void shouldGetPositionById() {
        //given
        Long id = addedPosition.getId();
        //when
        PositionDto foundPosition = positionService.getPositionById(id);
        //then
        assertNotNull(foundPosition);
        assertEquals(addedPosition.getId(), foundPosition.getId());
        assertEquals(addedPosition.getPositionName(), foundPosition.getPositionName());
        assertEquals(addedPosition.getCareerPath().getId(), foundPosition.getCareerPathId());
    }

    @Test
    void shouldGetPositionByName() {
        //given
        String name = "other name";
        PositionEntity otherPosition = createExamplePosition(careerPathEntity);
        otherPosition.setPositionName(name);
        PositionEntity savedOtherPosition = positionRepository.save(otherPosition);
        //when
        PositionDto foundPosition = positionService.getPositionByName(name);
        //then
        assertNotNull(foundPosition);
        assertEquals(savedOtherPosition.getId(), foundPosition.getId());
        assertEquals(savedOtherPosition.getPositionName(), foundPosition.getPositionName());
    }

    @Test
    void shouldUpdatePosition() {
        //given
        String oldPositionName = addedPosition.getPositionName();
        PositionDto positionUpdate = new PositionDto();
        positionUpdate.setId(addedPosition.getId());
        positionUpdate.setPositionName("new name");
        positionUpdate.setCareerPathId(careerPathEntity.getId());
        //when
        PositionDto updated = positionService.updatePosition(positionUpdate);
        //then
        assertNotEquals(oldPositionName, updated.getPositionName());
        assertEquals(positionUpdate.getPositionName(), updated.getPositionName());
    }

    @Test
    void shouldGetAllPositions() {
        //given
        //when
        List<PositionDto> foundPositions = positionService.getAllPositions();
        //then
        assertNotNull(foundPositions);
    }

    @Test
    void shouldGetPositionByCareerPathName() {
        //given
        CareerPathEntity otherCareerPath = careerPathRepository.save(createExampleCareerPath());
        positionRepository.save(createExamplePosition(otherCareerPath));
        positionRepository.save(createExamplePosition(otherCareerPath));
        String careerPathName = otherCareerPath.getCareerPathName();
        //when
        List<PositionDto> foundPositions = positionService.getAllPositionsFilteredByCareerPath(careerPathName);
        //then
        assertNotNull(foundPositions);
        assertEquals(2, foundPositions.size());
        assertEquals(0, foundPositions.stream().filter(position -> !position.getCareerPathId().equals(otherCareerPath.getId())).count());
    }

    @Test
    void shouldArchiveAllPositionsByCareerPathId() {
        //given
        CareerPathEntity otherCareerPath = careerPathRepository.save(createExampleCareerPath());
        PositionEntity position1 = positionRepository.save(createExamplePosition(otherCareerPath));
        PositionEntity position2 = positionRepository.save(createExamplePosition(otherCareerPath));
        Long careerPathId = otherCareerPath.getId();
        //when
        positionService.archiveAllPositionsByCareerPathId(careerPathId);
        //then
        assertEquals("unassigned", position1.getPositionName());
        assertEquals("unassigned", position2.getPositionName());
    }

    @Test
    void shouldArchivePositionById() {
        //given
        Long positionId = addedPosition.getId();
        //when
        positionService.archivePositionById(positionId);
        //then
        assertEquals("unassigned", addedPosition.getPositionName());
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
}