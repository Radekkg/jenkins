package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.CompetencyEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.dto.CompetencyDto;

import java.util.List;
import java.util.stream.Collectors;

public class CompetencyMapper {

    private CompetencyMapper() {
    }

    public static CompetencyEntity mapToEntity(CompetencyDto competencyDto, PositionEntity positionEntity) {
        CompetencyEntity competencyEntity = new CompetencyEntity();
        competencyEntity.setPosition(positionEntity);
        competencyEntity.setId(competencyDto.getId());
        competencyEntity.setDescription(competencyDto.getDescription());
        return competencyEntity;
    }

    public static CompetencyDto mapToDto(CompetencyEntity competencyEntity) {
        CompetencyDto competencyDto = new CompetencyDto();
        competencyDto.setId(competencyEntity.getId());
        competencyDto.setDescription(competencyEntity.getDescription());
        competencyDto.setPositionName(competencyEntity.getPosition().getPositionName());
        return competencyDto;
    }

    public static List<CompetencyDto> mapToDtoList(List<CompetencyEntity> competencyEntities) {
        return competencyEntities.stream().map((CompetencyMapper::mapToDto)).collect(Collectors.toList());
    }
}
