package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.domain.CompetencyEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.dto.CompetencyDto;
import com.capgemini.pdp.dto.CompetencyForUserDto;
import com.capgemini.pdp.mapper.CompetencyForUserMapper;
import com.capgemini.pdp.mapper.CompetencyMapper;
import com.capgemini.pdp.repository.CompetencyRepository;
import com.capgemini.pdp.repository.PositionRepository;
import com.capgemini.pdp.repository.UserRepository;
import com.capgemini.pdp.service.CompetencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CompetencyServiceImpl implements CompetencyService {

    private final CompetencyRepository competencyRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    @Autowired
    public CompetencyServiceImpl(CompetencyRepository competencyRepository, PositionRepository positionRepository, UserRepository userRepository) {
        this.competencyRepository = competencyRepository;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CompetencyDto addCompetency(CompetencyDto competencyDto) {
        PositionEntity positionEntity = positionRepository.findByPositionName(competencyDto.getPositionName());
        CompetencyEntity competency = competencyRepository.save(CompetencyMapper.mapToEntity(competencyDto, positionEntity));
        return CompetencyMapper.mapToDto(competency);
    }

    @Override
    public CompetencyDto getCompetencyById(Long id) {
        CompetencyEntity competency = competencyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return CompetencyMapper.mapToDto(competency);
    }

    @Override
    public CompetencyDto updateCompetency(CompetencyDto competencyDto) {
        CompetencyEntity competencyToUpdate = competencyRepository.findById(competencyDto.getId()).orElseThrow(NoSuchElementException::new);
        if (competencyDto.getPositionName() == null
                || positionRepository.findByPositionName(competencyDto.getPositionName()) == null) {
            throw new NoSuchElementException();
        }
        PositionEntity position = positionRepository.findByPositionName(competencyDto.getPositionName());
        competencyToUpdate.setPosition(position);
        competencyToUpdate.setDescription(competencyDto.getDescription());

        CompetencyEntity savedCompetency = competencyRepository.save(competencyToUpdate);
        return CompetencyMapper.mapToDto(savedCompetency);
    }

    @Override
    public List<CompetencyDto> getAllCompetencies() {
        List<CompetencyEntity> competencies = competencyRepository.findAll();
        return CompetencyMapper.mapToDtoList(competencies);
    }

    @Override
    public List<CompetencyDto> getAllByPositionName(String positionName) {
        PositionEntity position = positionRepository.findByPositionName(positionName);
        if (position == null) {
            throw new NoSuchElementException();
        }
        List<CompetencyEntity> competencies = competencyRepository.findAllByPositionId(position.getId());
        return CompetencyMapper.mapToDtoList(competencies);
    }

    @Override
    public void archiveAllCompetenciesByPositionId(Long id) {
        List<CompetencyEntity> competencyEntities = competencyRepository.findAllByPositionId(id);
        competencyEntities.forEach(competencyEntity ->
            archiveCompetency(competencyEntity.getId()));
    }

    @Override
    public void archiveCompetency(Long id) {
        CompetencyEntity competencyEntity = getByIdOrThrow(id);
        String description = competencyEntity.getDescription();
        competencyEntity.setDescription(description + "(archived)");
        competencyRepository.save(competencyEntity);
    }

    @Override
    public List<CompetencyForUserDto> getCompetenciesAvailableForUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<CompetencyEntity> competencyEntities = competencyRepository.findAllByPositionId(user.getPosition().getId());
        return CompetencyForUserMapper.mapToDtoList(competencyEntities);
    }

    private CompetencyEntity getByIdOrThrow(Long id) {
        return competencyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
