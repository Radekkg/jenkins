package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.dto.CareerPathDto;
import com.capgemini.pdp.mapper.CareerPathMapper;
import com.capgemini.pdp.repository.CareerPathRepository;
import com.capgemini.pdp.service.CareerPathService;
import com.capgemini.pdp.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CareerPathServiceImpl implements CareerPathService {

    private final CareerPathRepository careerPathRepository;
    private final PositionService positionService;

    public CareerPathServiceImpl(CareerPathRepository careerPathRepository, PositionService positionService) {
        this.careerPathRepository = careerPathRepository;
        this.positionService = positionService;
    }

    @Override
    public CareerPathDto addCareerPath(CareerPathDto careerPathDto) {
        CareerPathEntity savedCareerPath = careerPathRepository.save(CareerPathMapper.mapToEntity(careerPathDto));
        return CareerPathMapper.mapToDto(savedCareerPath);
    }

    @Override
    public CareerPathDto getCareerPathById(Long id) {
        return CareerPathMapper.mapToDto(careerPathRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public CareerPathDto getCareerPathByName(String careerPathName) {
        CareerPathEntity found = careerPathRepository.findByCareerPathName(careerPathName);
        if (found == null) {
            throw new NoSuchElementException();
        }
        return CareerPathMapper.mapToDto(found);
    }

    @Override
    public CareerPathDto updateCareerPath(CareerPathDto careerPathDto) {
        CareerPathEntity careerPathToUpdate = getByIdOrThrow(careerPathDto.getId());
        careerPathToUpdate.setCareerPathName(careerPathDto.getCareerPathName());
        CareerPathEntity savedCareerPath = careerPathRepository.save(careerPathToUpdate);
        return CareerPathMapper.mapToDto(savedCareerPath);
    }

    @Override
    public void archiveCareerPath(Long id) {
        CareerPathEntity careerPathEntity = getByIdOrThrow(id);
        positionService.archiveAllPositionsByCareerPathId(careerPathEntity.getId());
        careerPathEntity.setCareerPathName("unassigned");
        careerPathRepository.save(careerPathEntity);
    }

    @Override
    public List<CareerPathDto> getAllCareerPaths() {
        return CareerPathMapper.mapToDtoList(careerPathRepository.findAll());
    }

    private CareerPathEntity getByIdOrThrow(Long id) {
        return careerPathRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
