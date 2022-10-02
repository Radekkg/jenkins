package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.dto.CareerPathDto;

import java.util.List;
import java.util.stream.Collectors;

public class CareerPathMapper {

    private CareerPathMapper() {
    }

    public static CareerPathEntity mapToEntity(CareerPathDto careerPathDto) {
        CareerPathEntity careerPathEntity = new CareerPathEntity();
        careerPathEntity.setId(careerPathDto.getId());
        careerPathEntity.setCareerPathName(careerPathDto.getCareerPathName());
        return careerPathEntity;
    }

    public static CareerPathDto mapToDto(CareerPathEntity careerPathEntity) {
        CareerPathDto careerPathDto = new CareerPathDto();
        careerPathDto.setId(careerPathEntity.getId());
        careerPathDto.setCareerPathName(careerPathEntity.getCareerPathName());
        return careerPathDto;
    }

    public static List<CareerPathDto> mapToDtoList(List<CareerPathEntity> careerPathEntities) {
        return careerPathEntities.stream()
                .map(CareerPathMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
