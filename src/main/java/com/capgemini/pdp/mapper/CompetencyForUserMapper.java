package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.CompetencyEntity;
import com.capgemini.pdp.dto.CompetencyForUserDto;

import java.util.List;
import java.util.stream.Collectors;

public class CompetencyForUserMapper {
    private CompetencyForUserMapper() {
    }

    public static CompetencyForUserDto mapToDto(CompetencyEntity competencyEntity) {
        CompetencyForUserDto competencyForUserDto = new CompetencyForUserDto();
        competencyForUserDto.setId(competencyEntity.getId());
        competencyForUserDto.setDescription(competencyEntity.getDescription());
        return competencyForUserDto;
    }

    public static List<CompetencyForUserDto> mapToDtoList(List<CompetencyEntity> competencyEntities) {
        return competencyEntities.stream().map((CompetencyForUserMapper::mapToDto)).collect(Collectors.toList());
    }
}
