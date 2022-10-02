package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.UserDto;

public class UserMapper {

    private UserMapper() {
    }

    public static UserEntity mapToEntity(UserDto userDto, UserEntity supervisor, PositionEntity position) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setRoleName(UserRole.valueOf(userDto.getRoleName()));
        userEntity.setSupervisor(supervisor);
        userEntity.setPosition(position);
        return userEntity;
    }

    public static UserDto mapToDto(UserEntity userEntity) {
        Long supervisorId = null;
        if (userEntity.getSupervisor() != null) {
            supervisorId = userEntity.getSupervisor().getId();
        }
        Long positionId = null;
        if (userEntity.getPosition() != null) {
            positionId = userEntity.getPosition().getId();
        }
        return UserDto.builder()
                .id(userEntity.getId())
                .roleName(userEntity.getRoleName().toString())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .supervisorId(supervisorId)
                .positionId(positionId)
                .build();
    }

}
