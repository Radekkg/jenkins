package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.DevelopmentActivityEntity;
import com.capgemini.pdp.dto.DevelopmentActivityDto;

import java.util.List;
import java.util.stream.Collectors;

public class DevelopmentActivityMapper {
    private DevelopmentActivityMapper() {
    }

    public static DevelopmentActivityDto mapToDto(DevelopmentActivityEntity developmentActivityEntity) {
        return DevelopmentActivityDto.builder()
                .id(developmentActivityEntity.getId())
                .developmentAction(developmentActivityEntity.getDevelopmentAction())
                .activityStatus(developmentActivityEntity.getActivityStatus())
                .comment(developmentActivityEntity.getComment())
                .competencyId(developmentActivityEntity.getCompetency().getId())
                .planId(developmentActivityEntity.getPlan().getId())
                .typeId(developmentActivityEntity.getType().getId())
                .build();
    }

    public static List<DevelopmentActivityDto> mapToDtoList(List<DevelopmentActivityEntity> developmentActivityEntities) {
        return developmentActivityEntities.stream().map((DevelopmentActivityMapper::mapToDto)).collect(Collectors.toList());
    }
}
