package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.CompetencyEntity;
import com.capgemini.pdp.domain.DevelopmentActivityEntity;
import com.capgemini.pdp.domain.PlanEntity;
import com.capgemini.pdp.domain.TypeEntity;
import com.capgemini.pdp.domain.enumerated.ActivityStatus;
import com.capgemini.pdp.dto.DevelopmentActivityToAddDto;

public class DevelopmentActivityToSaveMapper {
    private DevelopmentActivityToSaveMapper() {
    }

    public static DevelopmentActivityEntity mapToEntity(DevelopmentActivityToAddDto developmentActivityToAddDto,
                                                        CompetencyEntity competencyEntity, PlanEntity planEntity,
                                                        TypeEntity typeEntity) {
        DevelopmentActivityEntity developmentActivityEntity = new DevelopmentActivityEntity();
        developmentActivityEntity.setDevelopmentAction(developmentActivityToAddDto.getDevelopmentAction());
        developmentActivityEntity.setActivityStatus(ActivityStatus.NOT_STARTED);
        developmentActivityEntity.setComment(developmentActivityToAddDto.getComment());
        developmentActivityEntity.setCompetency(competencyEntity);
        developmentActivityEntity.setPlan(planEntity);
        developmentActivityEntity.setType(typeEntity);
        return developmentActivityEntity;
    }

    public static DevelopmentActivityToAddDto mapToDto(DevelopmentActivityEntity developmentActivityEntity) {
        return DevelopmentActivityToAddDto.builder()
                .userId(developmentActivityEntity.getPlan().getUser().getId())
                .deadline(developmentActivityEntity.getPlan().getYear())
                .developmentAction(developmentActivityEntity.getDevelopmentAction())
                .typeId(developmentActivityEntity.getType().getId())
                .competencyId(developmentActivityEntity.getCompetency().getId())
                .comment(developmentActivityEntity.getComment())
                .build();
    }
}
