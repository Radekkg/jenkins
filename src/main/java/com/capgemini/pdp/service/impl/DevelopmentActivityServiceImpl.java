package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.domain.CompetencyEntity;
import com.capgemini.pdp.domain.DevelopmentActivityEntity;
import com.capgemini.pdp.domain.PlanEntity;
import com.capgemini.pdp.domain.TypeEntity;
import com.capgemini.pdp.domain.enumerated.ActivityStatus;
import com.capgemini.pdp.dto.ChangeActivityStatusForUserDto;
import com.capgemini.pdp.dto.DevelopmentActivityDto;
import com.capgemini.pdp.dto.DevelopmentActivityForUserDto;
import com.capgemini.pdp.dto.DevelopmentActivityToAddDto;
import com.capgemini.pdp.dto.DevelopmentActivityToEditDto;
import com.capgemini.pdp.mapper.DevelopmentActivityForUserMapper;
import com.capgemini.pdp.mapper.DevelopmentActivityMapper;
import com.capgemini.pdp.mapper.DevelopmentActivityToEditMapper;
import com.capgemini.pdp.mapper.DevelopmentActivityToSaveMapper;
import com.capgemini.pdp.repository.*;
import com.capgemini.pdp.service.DevelopmentActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.util.EnumUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class DevelopmentActivityServiceImpl implements DevelopmentActivityService {

    private final DevelopmentActivityRepository developmentActivityRepository;
    private final PlanRepository planRepository;
    private final CompetencyRepository competencyRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;

    @Autowired
    public DevelopmentActivityServiceImpl(DevelopmentActivityRepository developmentActivityRepository, PlanRepository planRepository, CompetencyRepository competencyRepository, TypeRepository typeRepository, UserRepository userRepository) {
        this.developmentActivityRepository = developmentActivityRepository;
        this.planRepository = planRepository;
        this.competencyRepository = competencyRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DevelopmentActivityDto getDevelopmentActivityById(Long id) {
        DevelopmentActivityEntity developmentActivity = developmentActivityRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return DevelopmentActivityMapper.mapToDto(developmentActivity);
    }

    @Override
    public DevelopmentActivityToEditDto updateDevelopmentActivityForUserAndPlan(DevelopmentActivityToEditDto developmentActivityToEditDto) {
        DevelopmentActivityEntity developmentActivityToUpdate = developmentActivityRepository.findById(developmentActivityToEditDto.getDevelopmentActivityId()).orElseThrow(NoSuchElementException::new);

        developmentActivityToUpdate.setDevelopmentAction(developmentActivityToEditDto.getDevelopmentAction());
        developmentActivityToUpdate.setComment(developmentActivityToEditDto.getComment());
        developmentActivityToUpdate.setActivityStatus(ActivityStatus.valueOf(developmentActivityToEditDto.getStatus()));
        DevelopmentActivityEntity developmentActivity = developmentActivityRepository.save(developmentActivityToUpdate);

        return DevelopmentActivityToEditMapper.mapToDto(developmentActivity);
    }

    @Override
    public void removeDevelopmentActivityById(Long id) {
        developmentActivityRepository.deleteById(id);
    }

    @Override
    public List<DevelopmentActivityDto> getAllDevelopmentActivities() {
        List<DevelopmentActivityEntity> developmentActivities = developmentActivityRepository.findAll();
        return DevelopmentActivityMapper.mapToDtoList(developmentActivities);
    }

    @Override
    public List<DevelopmentActivityForUserDto> getDevelopmentActivitiesByUserIdAndDates(Long userId, Integer datePlanFrom, Integer datePlanUntil) {
        if (datePlanFrom == null && datePlanUntil == null) {
            int currentYear = LocalDate.now().getYear();
            datePlanFrom = currentYear;
            datePlanUntil = currentYear;
        }
        List<DevelopmentActivityEntity> developmentActivities = developmentActivityRepository.findDevelopmentActivitiesByUserIdAndDates(userId, datePlanFrom, datePlanUntil);
        return DevelopmentActivityForUserMapper.mapToDtoList(developmentActivities);
    }

    @Override
    public DevelopmentActivityToAddDto addDevelopmentActivityForUserAndPlan(DevelopmentActivityToAddDto developmentActivityToAddDto) {
        PlanEntity planEntity = planRepository.findByUserIdAndDeadline(developmentActivityToAddDto.getUserId(), developmentActivityToAddDto.getDeadline());
        if (planEntity == null) {
            planEntity = new PlanEntity();
            planEntity.setYear(developmentActivityToAddDto.getDeadline());
            planEntity.setUser(userRepository.findById(developmentActivityToAddDto.getUserId()).orElseThrow(NoSuchElementException::new));
            planRepository.save(planEntity);
        }
        TypeEntity typeEntity = typeRepository.findById(developmentActivityToAddDto.getTypeId()).orElseThrow(NoSuchElementException::new);
        CompetencyEntity competencyEntity = competencyRepository.findById(developmentActivityToAddDto.getCompetencyId()).orElseThrow(NoSuchElementException::new);
        DevelopmentActivityEntity developmentActivity = developmentActivityRepository.save(DevelopmentActivityToSaveMapper.mapToEntity(developmentActivityToAddDto, competencyEntity, planEntity, typeEntity));
        return DevelopmentActivityToSaveMapper.mapToDto(developmentActivity);
    }

    @Override
    public DevelopmentActivityDto changeDevelopmentActivityStatus(ChangeActivityStatusForUserDto activityStatus) {
        DevelopmentActivityEntity activityEntity = developmentActivityRepository.findById(activityStatus.getActivityId()).orElseThrow(NoSuchElementException::new);
        checkActivityStatus(activityStatus.getStatus());
        activityEntity.setActivityStatus(ActivityStatus.valueOf(activityStatus.getStatus().toUpperCase()));
        return DevelopmentActivityMapper.mapToDto(developmentActivityRepository.save(activityEntity));
    }

    private void checkActivityStatus(String activityStatus){
        EnumUtils.findEnumInsensitiveCase(ActivityStatus.class, activityStatus);
    }
}
