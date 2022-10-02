package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.dto.PositionDto;
import com.capgemini.pdp.mapper.PositionMapper;
import com.capgemini.pdp.repository.CareerPathRepository;
import com.capgemini.pdp.repository.PositionRepository;
import com.capgemini.pdp.service.CompetencyService;
import com.capgemini.pdp.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final CareerPathRepository careerPathRepository;
    private final CompetencyService competencyService;

    @Autowired
    public PositionServiceImpl(PositionRepository positionRepository, CareerPathRepository careerPathRepository, CompetencyService competencyService) {
        this.positionRepository = positionRepository;
        this.careerPathRepository = careerPathRepository;
        this.competencyService = competencyService;
    }

    @Override
    public PositionDto addNewPositionToCareerPath(PositionDto positionDto) {
        CareerPathEntity careerPathEntity = careerPathRepository.findById(positionDto.getCareerPathId()).orElseThrow(NoSuchElementException::new);
        PositionEntity positionToSave = PositionMapper.mapToEntity(positionDto, careerPathEntity);

        PositionEntity savedPosition = positionRepository.save(positionToSave);
        return PositionMapper.mapToDto(savedPosition);
    }

    @Override
    public PositionDto getPositionById(Long id) {
        return PositionMapper.mapToDto(positionRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    @Override
    public PositionDto getPositionByName(String positionName) {
        return PositionMapper.mapToDto(positionRepository.findByPositionName(positionName));
    }

    @Override
    public PositionDto updatePosition(PositionDto positionDto) {
        CareerPathEntity careerPathEntity = careerPathRepository.findById(positionDto.getCareerPathId()).orElseThrow(NoSuchElementException::new);
        PositionEntity positionToUpdate = positionRepository.findById(positionDto.getId()).orElseThrow(NoSuchElementException::new);

        positionToUpdate.setPositionName(positionDto.getPositionName());
        positionToUpdate.setCareerPath(careerPathEntity);

        PositionEntity savedPosition = positionRepository.save(positionToUpdate);
        return PositionMapper.mapToDto(savedPosition);
    }

    @Override
    public List<PositionDto> getAllPositions() {
        return PositionMapper.mapToDtoList(positionRepository.findAll());
    }

    @Override
    public List<PositionDto> getAllPositionsFilteredByCareerPath(String careerPathName) {
        CareerPathEntity careerPathEntity = careerPathRepository.findByCareerPathName(careerPathName);
        return PositionMapper.mapToDtoList(positionRepository.findAllByCareerPathId(careerPathEntity.getId()));
    }

    @Override
    public void archiveAllPositionsByCareerPathId(Long id) {
        List<PositionEntity> allByCareerPathId = positionRepository.findAllByCareerPathId(id);
        allByCareerPathId.forEach(positionEntity -> archivePositionById(positionEntity.getId()));
    }

    @Override
    public void archivePositionById(Long id) {
        PositionEntity positionEntity = positionRepository.getById(id);
        positionEntity.setPositionName("unassigned");
        competencyService.archiveAllCompetenciesByPositionId(positionEntity.getId());
        positionRepository.save(positionEntity);
    }

}
