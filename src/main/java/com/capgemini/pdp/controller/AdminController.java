package com.capgemini.pdp.controller;

import com.capgemini.pdp.dto.*;
import com.capgemini.pdp.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/competency/{position}")
    public ResponseEntity<List<CompetencyDto>> getAllCareerPaths(@PathVariable String position) {
        List<CompetencyDto> competencies = adminService.getAllCompetencyDtoFilteredByPosition(position);
        return ResponseEntity.status(HttpStatus.OK).body(competencies);
    }

    @PostMapping("/careerPath")
    public ResponseEntity<CareerPathDto> addCareerPath(@RequestBody CareerPathDto careerPath) {
        CareerPathDto addedCareerPath = adminService.addNewCareerPath(careerPath);
        return ResponseEntity.status(HttpStatus.OK).body(addedCareerPath);
    }

    @PutMapping("/careerPath/{id}")
    public ResponseEntity<CareerPathDto> updateCareerPath(@RequestBody CareerPathDto careerPath) {
        CareerPathDto updatedCareerPath = adminService.editCareerPath(careerPath);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCareerPath);
    }

    @DeleteMapping("/careerPath/{id}")
    public ResponseEntity<CareerPathDto> removeCareerPathById(@PathVariable Long id) {
        adminService.archiveCareerPath(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/position")
    public ResponseEntity<PositionDto> addPosition(@RequestBody PositionDto positionDto) {
        PositionDto addedPosition = adminService.addNewPositionToCareerPath(positionDto);
        return ResponseEntity.status(HttpStatus.OK).body(addedPosition);
    }


    @PutMapping("/position/{id}")
    public ResponseEntity<PositionDto> updatePosition(@RequestBody PositionDto position) {
        PositionDto updatedPosition = adminService.editPosition(position);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPosition);
    }

    @DeleteMapping("/position/{id}")
    public ResponseEntity<PositionDto> removePositionById(@PathVariable Long id) {
        adminService.archivePosition(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/competency")
    public ResponseEntity<CompetencyDto> addCompetency(@RequestBody CompetencyDto competency) {
        CompetencyDto addedCompetency = adminService.addNewCompetencyToPosition(competency);
        return ResponseEntity.status(HttpStatus.OK).body(addedCompetency);
    }

    @PutMapping("/competency/{id}")
    public ResponseEntity<CompetencyDto> updateCompetency(@RequestBody CompetencyDto competency) {
        CompetencyDto updatedCompetency = adminService.editCompetency(competency);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCompetency);
    }

    @DeleteMapping("/competency/{id}")
    public ResponseEntity<CompetencyDto> removeCompetencyById(@PathVariable Long id) {
        adminService.archiveCompetency(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/employee/{position}")
    public ResponseEntity<List<UserForManagerAndAdminViewDto>> getAllUsers(@PathVariable String position) {
        List<UserForManagerAndAdminViewDto> users = adminService.getAllUsersFilteredByPosition(position);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/employee")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) {
        UserDto addedUser = adminService.addNewEmployee(user);
        return ResponseEntity.status(HttpStatus.OK).body(addedUser);
    }

    @PutMapping("/employee")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        UserDto editedUser = adminService.editEmployee(user);
        return ResponseEntity.status(HttpStatus.OK).body(editedUser);
    }

    @PostMapping("/employee/{id}/{role}")
    public ResponseEntity<UserDto> changeUserRole(@PathVariable Long id, @PathVariable String role) {
        UserDto updatedUser = adminService.changeEmployeeRole(id, role);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<UserDto> removeUserById(@PathVariable Long id) {
        adminService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
