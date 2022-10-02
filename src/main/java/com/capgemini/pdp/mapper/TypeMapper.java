package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.TypeEntity;
import com.capgemini.pdp.dto.TypeDto;

import java.util.List;
import java.util.stream.Collectors;

public class TypeMapper {
    private TypeMapper() {
    }

    public static TypeDto mapToDto(TypeEntity typeEntity) {
        TypeDto typeDto = new TypeDto();
        typeDto.setId(typeEntity.getId());
        typeDto.setDescription(typeEntity.getDescription());
        return typeDto;
    }

    public static List<TypeDto> mapToDtoList(List<TypeEntity> typeEntities) {
        return typeEntities.stream().map((TypeMapper::mapToDto)).collect(Collectors.toList());
    }
}
