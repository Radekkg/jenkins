package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.dto.ChangeCurrentCareerPathForUserDto;
import com.capgemini.pdp.dto.UserDto;
import com.capgemini.pdp.dto.UserForManagerAndAdminViewDto;
import com.capgemini.pdp.dto.UserInfoDto;

import java.util.List;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto getUserById(Long id);

    UserDto updateUser(UserDto userDto);

    void removeUser(Long id);

    List<UserForManagerAndAdminViewDto> getAllUsers();

    UserInfoDto getUserInfo(Long userId);

    UserInfoDto editUserProfile(UserInfoDto userInfoDto);

    UserEntity getUserByIdOrThrow(Long id);

    List<UserForManagerAndAdminViewDto> getAllUsersByPositionName(String positionName);

    UserInfoDto changeCurrentCareerPath(ChangeCurrentCareerPathForUserDto newCareerPath);

}
