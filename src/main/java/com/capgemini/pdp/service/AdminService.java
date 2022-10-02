package com.capgemini.pdp.service;

import com.capgemini.pdp.dto.*;

import java.util.List;

public interface AdminService {

    List<CompetencyDto> getAllCompetencyDtoFilteredByPosition(String positionName);

    CareerPathDto addNewCareerPath(CareerPathDto careerPathDto);

    CareerPathDto editCareerPath(CareerPathDto careerPathDto);

    void archiveCareerPath(Long id);

    PositionDto addNewPositionToCareerPath(PositionDto positionDto);

    PositionDto editPosition(PositionDto positionDto);

    void archivePosition(Long id);

    CompetencyDto addNewCompetencyToPosition(CompetencyDto competencyDto);

    CompetencyDto editCompetency(CompetencyDto competencyDto);

    void archiveCompetency(Long id);

    List<UserForManagerAndAdminViewDto> getAllUsersFilteredByPosition(String positionName);

    UserDto addNewEmployee(UserDto userDto);

    UserDto editEmployee(UserDto userDto);

    UserDto changeEmployeeRole(Long userId, String roleName);

    void deleteEmployee(Long id);

}
