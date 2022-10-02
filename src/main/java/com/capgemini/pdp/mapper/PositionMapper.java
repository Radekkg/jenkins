package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.dto.PositionDto;

import java.util.List;
import java.util.stream.Collectors;

public class PositionMapper {

    private PositionMapper() {
    }

    public static PositionEntity mapToEntity(PositionDto positionDto, CareerPathEntity careerPath) {
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setId(positionDto.getId());
        positionEntity.setPositionName(positionDto.getPositionName());
        positionEntity.setCareerPath(careerPath);
        return positionEntity;
    }

    public static PositionDto mapToDto(PositionEntity positionEntity) {
        PositionDto positionDto = new PositionDto();
        positionDto.setId(positionEntity.getId());
        positionDto.setPositionName(positionEntity.getPositionName());
        positionDto.setCareerPathId(positionEntity.getCareerPath().getId());
        return positionDto;
    }

    public static List<PositionDto> mapToDtoList(List<PositionEntity> positionEntities) {
        return positionEntities.stream()
                .map(PositionMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
