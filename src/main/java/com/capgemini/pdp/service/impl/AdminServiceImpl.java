package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.dto.*;
import com.capgemini.pdp.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final CompetencyService competencyService;
    private final CareerPathService careerPathService;
    private final PositionService positionService;
    private final UserService userService;

    public AdminServiceImpl(CompetencyService competencyService, CareerPathService careerPathService, PositionService positionService, UserService userService) {
        this.competencyService = competencyService;
        this.careerPathService = careerPathService;
        this.positionService = positionService;
        this.userService = userService;
    }

    @Override
    public List<CompetencyDto> getAllCompetencyDtoFilteredByPosition(String positionName) {
        return competencyService.getAllByPositionName(positionName);
    }

    @Override
    public CareerPathDto addNewCareerPath(CareerPathDto careerPathDto) {
        return careerPathService.addCareerPath(careerPathDto);
    }

    @Override
    public CareerPathDto editCareerPath(CareerPathDto careerPathDto) {
        return careerPathService.updateCareerPath(careerPathDto);
    }

    @Override
    public void archiveCareerPath(Long id) {
        careerPathService.archiveCareerPath(id);
    }

    @Override
    public PositionDto addNewPositionToCareerPath(PositionDto positionDto) {
        return positionService.addNewPositionToCareerPath(positionDto);
    }

    @Override
    public PositionDto editPosition(PositionDto positionDto) {
        return positionService.updatePosition(positionDto);
    }

    @Override
    public void archivePosition(Long id) {
        positionService.archivePositionById(id);
    }

    @Override
    public CompetencyDto addNewCompetencyToPosition(CompetencyDto competencyDto) {
        return competencyService.addCompetency(competencyDto);
    }

    @Override
    public CompetencyDto editCompetency(CompetencyDto competencyDto) {
        return competencyService.updateCompetency(competencyDto);
    }

    @Override
    public void archiveCompetency(Long id) {
        competencyService.archiveCompetency(id);
    }

    @Override
    public List<UserForManagerAndAdminViewDto> getAllUsersFilteredByPosition(String positionName) {
        return userService.getAllUsersByPositionName(positionName);
    }

    @Override
    public UserDto addNewEmployee(UserDto userDto) {
        return userService.addUser(userDto);
    }

    @Override
    public UserDto editEmployee(UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @Override
    public UserDto changeEmployeeRole(Long userId, String roleName) {
        UserDto userDto = userService.getUserById(userId);
        userDto.setRoleName(roleName);
        return userService.updateUser(userDto);
    }

    @Override
    public void deleteEmployee(Long id) {
        userService.removeUser(id);
    }

}
