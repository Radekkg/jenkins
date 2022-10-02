package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.ChangeCurrentCareerPathForUserDto;
import com.capgemini.pdp.dto.UserDto;
import com.capgemini.pdp.dto.UserForManagerAndAdminViewDto;
import com.capgemini.pdp.dto.UserInfoDto;
import com.capgemini.pdp.mapper.UserForManagerAndAdminViewMapper;
import com.capgemini.pdp.mapper.UserInfoMapper;
import com.capgemini.pdp.mapper.UserMapper;
import com.capgemini.pdp.repository.PositionRepository;
import com.capgemini.pdp.repository.UserRepository;
import com.capgemini.pdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        UserEntity supervisor = null;
        PositionEntity position = null;
        if (userDto.getSupervisorId() != null) {
            supervisor = getUserByIdOrThrow(userDto.getSupervisorId());
        }
        if (userDto.getPositionId() != null) {
            position = positionRepository.findById(userDto.getPositionId()).orElseThrow(NoSuchElementException::new);
        }
        
        UserEntity userToSave = UserMapper.mapToEntity(userDto, supervisor, position);
        UserEntity savedUser = userRepository.save(userToSave);
        return UserMapper.mapToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        return UserMapper.mapToDto(userRepository.getById(id));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity userToUpdate = userRepository.findById(userDto.getId()).orElseThrow(NoSuchElementException::new);
        UserEntity supervisor = null;
        PositionEntity position = null;
        if (userDto.getSupervisorId() != null) {
            supervisor = getUserByIdOrThrow(userDto.getSupervisorId());
        }
        if (userDto.getPositionId() != null) {
            position = positionRepository.findById(userDto.getPositionId()).orElseThrow(NoSuchElementException::new);
        }
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setPassword(userToUpdate.getPassword());
        userToUpdate.setRoleName(UserRole.valueOf(userDto.getRoleName()));
        userToUpdate.setSupervisor(supervisor);
        userToUpdate.setPosition(position);

        UserEntity savedUser = userRepository.save(userToUpdate);
        return UserMapper.mapToDto(savedUser);
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserForManagerAndAdminViewDto> getAllUsers() {
        return UserForManagerAndAdminViewMapper.mapToDtoList(userRepository.findAll());
    }

    @Override
    public UserInfoDto getUserInfo(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return UserInfoMapper.mapToDto(user);
    }

    @Override
    public UserInfoDto editUserProfile(UserInfoDto userInfoDto) {
        UserEntity userToUpdate = userRepository.findById(userInfoDto.getId()).orElseThrow(NoSuchElementException::new);
        UserEntity supervisor = null;
        if (userInfoDto.getSupervisorEmail() != null) {
            supervisor = userRepository.findByEmail(userInfoDto.getSupervisorEmail());
            checkSupervisor(supervisor, userInfoDto.getSupervisorEmail());
        }
        userToUpdate.setFirstName(userInfoDto.getUserFirstName());
        userToUpdate.setLastName(userInfoDto.getUserLastName());
        userToUpdate.setSupervisor(supervisor);
        userToUpdate.setCareerGoal(userInfoDto.getCareerGoal());

        UserEntity savedUser = userRepository.save(userToUpdate);
        return UserInfoMapper.mapToDto(savedUser);
    }

    private void checkSupervisor(UserEntity supervisor, String email) {
        if (supervisor == null || !supervisor.getRoleName().equals(UserRole.MANAGER)) {
            throw new EntityNotFoundException("There is no manager with email: " + email);
        }
    }

    public UserEntity getUserByIdOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserForManagerAndAdminViewDto> getAllUsersByPositionName(String positionName) {
        return UserForManagerAndAdminViewMapper.mapToDtoList(userRepository.findAllByPositionName(positionName));
    }

    @Override
    public UserInfoDto changeCurrentCareerPath(ChangeCurrentCareerPathForUserDto newCareerPath) {
        UserEntity user = userRepository.findById(newCareerPath.getUserId()).orElseThrow(NoSuchElementException::new);
        user.getPosition().setPositionName(newCareerPath.getPositionName());
        user.getPosition().getCareerPath().setCareerPathName(newCareerPath.getCarrierPathName());
        return UserInfoMapper.mapToDto(user);
    }

}
