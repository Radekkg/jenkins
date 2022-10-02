package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.dto.CareerPathDto;
import com.capgemini.pdp.mapper.CareerPathMapper;
import com.capgemini.pdp.repository.CareerPathRepository;
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
class CareerPathServiceTest {

    @Autowired
    private CareerPathService careerPathService;
    @Autowired
    private CareerPathRepository careerPathRepository;

    private static int generator;
    private CareerPathEntity addedCareerPath;

    @BeforeEach
    void setUp() {
        addedCareerPath = careerPathRepository.save(createExampleCareerPath());
    }

    @Test
    void shouldAddNewCareerPath() {
        //given
        CareerPathDto careerPathDto = CareerPathMapper.mapToDto(createExampleCareerPath());
        //when
        CareerPathDto added = careerPathService.addCareerPath(careerPathDto);
        //then
        assertNotNull(added.getId());
        assertEquals(careerPathDto.getCareerPathName(), added.getCareerPathName());
    }

    @Test
    void shouldNotAddCareerPathWithoutName() {
        //given
        CareerPathDto careerPathDto = new CareerPathDto();
        careerPathDto.setCareerPathName(null);
        //when
        //then
        assertThrows(DataIntegrityViolationException.class, () -> careerPathService.addCareerPath(careerPathDto));
    }

    @Test
    void shouldGetCareerPathById() {
        //given
        Long id = addedCareerPath.getId();
        //when
        CareerPathDto foundCareerPath = careerPathService.getCareerPathById(id);
        //then
        assertNotNull(foundCareerPath);
        assertEquals(addedCareerPath.getId(), foundCareerPath.getId());
        assertEquals(addedCareerPath.getCareerPathName(), foundCareerPath.getCareerPathName());
    }

    @Test
    void shouldThrowExceptionWhenCareerPathWithProvidedIdDoesNotExist() {
        //given
        Long id = -1L;
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> careerPathService.getCareerPathById(id));
    }

    @Test
    void shouldGetCareerPathByName() {
        //given
        String name = addedCareerPath.getCareerPathName();
        //when
        CareerPathDto foundCareerPath = careerPathService.getCareerPathByName(name);
        //then
        assertNotNull(foundCareerPath);
        assertEquals(addedCareerPath.getId(), foundCareerPath.getId());
        assertEquals(addedCareerPath.getCareerPathName(), foundCareerPath.getCareerPathName());
    }

    @Test
    void shouldThrowExceptionWhenCareerPathWithProvidedNameDoesNotExist() {
        //given
        String name = "xxx";
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> careerPathService.getCareerPathByName(name));
    }

    @Test
    void shouldUpdateCareerPath() {
        //given
        String oldName = addedCareerPath.getCareerPathName();
        CareerPathDto careerPathDto = new CareerPathDto();
        careerPathDto.setId(addedCareerPath.getId());
        careerPathDto.setCareerPathName("new name");
        //when
        CareerPathDto updated = careerPathService.updateCareerPath(careerPathDto);
        //then
        assertNotEquals(oldName, updated.getCareerPathName());
        assertEquals(careerPathDto.getCareerPathName(), updated.getCareerPathName());
        assertEquals(addedCareerPath.getId(), updated.getId());
    }

    @Test
    void shouldArchiveCareerPath() {
        //given
        Long id = addedCareerPath.getId();
        //when
        careerPathService.archiveCareerPath(id);
        //then
        assertEquals("unassigned", addedCareerPath.getCareerPathName());
    }

    @Test
    void shouldGetAllCareerPaths() {
        //given
        //when
        List<CareerPathDto> foundCareerPaths = careerPathService.getAllCareerPaths();
        //then
        assertNotNull(foundCareerPaths);
    }

    private CareerPathEntity createExampleCareerPath() {
        CareerPathEntity careerPathEntity = new CareerPathEntity();
        careerPathEntity.setCareerPathName("name" + generator++);
        return careerPathEntity;
    }

}