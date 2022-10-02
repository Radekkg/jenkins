package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.DevelopmentActivityEntity;
import com.capgemini.pdp.dto.DevelopmentActivityForUserDto;

import java.util.List;
import java.util.stream.Collectors;

public class DevelopmentActivityForUserMapper {

    private DevelopmentActivityForUserMapper() {
    }

    public static DevelopmentActivityForUserDto mapToDto(DevelopmentActivityEntity entity) {
        return DevelopmentActivityForUserDto.builder()
                .id(entity.getId())
                .developmentAction(entity.getDevelopmentAction())
                .activityStatus(entity.getActivityStatus().toString())
                .type(entity.getType().getDescription())
                .comment(entity.getComment())
                .competencyName(entity.getCompetency().getDescription())
                .deadline(entity.getPlan().getYear())
                .build();
    }

    public static List<DevelopmentActivityForUserDto> mapToDtoList(List<DevelopmentActivityEntity> developmentActivities) {
        return developmentActivities.stream()
                .map(DevelopmentActivityForUserMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
