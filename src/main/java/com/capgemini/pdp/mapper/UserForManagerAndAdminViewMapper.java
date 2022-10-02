package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.dto.UserForManagerAndAdminViewDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserForManagerAndAdminViewMapper {
    private UserForManagerAndAdminViewMapper() {
    }

    public static UserForManagerAndAdminViewDto mapToDto(UserEntity userEntity) {
        return UserForManagerAndAdminViewDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .careerPath(userEntity.getPosition() != null ? userEntity.getPosition().getCareerPath().getCareerPathName() : null)
                .position(userEntity.getPosition() != null ? userEntity.getPosition().getPositionName() : null)
                .role(userEntity.getRoleName().toString())
                .build();
    }

    public static List<UserForManagerAndAdminViewDto> mapToDtoList(List<UserEntity> entities) {
        return entities.stream()
                .map(UserForManagerAndAdminViewMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
