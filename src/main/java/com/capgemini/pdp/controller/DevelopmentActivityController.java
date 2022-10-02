package com.capgemini.pdp.controller;

import com.capgemini.pdp.dto.*;
import com.capgemini.pdp.service.DevelopmentActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/development_activity")
public class DevelopmentActivityController {

    private final DevelopmentActivityService developmentActivityService;

    @Autowired
    public DevelopmentActivityController(DevelopmentActivityService developmentActivityService) {
        this.developmentActivityService = developmentActivityService;
    }

    @GetMapping
    public ResponseEntity<List<DevelopmentActivityDto>> getDevelopmentActivities() {
        List<DevelopmentActivityDto> developmentActivities = developmentActivityService.getAllDevelopmentActivities();
        return ResponseEntity.status(HttpStatus.OK).body(developmentActivities);
    }

    @GetMapping("/{developmentActivityId}")
    public ResponseEntity<DevelopmentActivityDto> getDevelopmentActivityById(@PathVariable Long developmentActivityId) {
        DevelopmentActivityDto developmentActivity = developmentActivityService.getDevelopmentActivityById(developmentActivityId);
        return ResponseEntity.status(HttpStatus.OK).body(developmentActivity);
    }

    @PostMapping
    public ResponseEntity<DevelopmentActivityToAddDto> addDevelopmentActivityForUserAndPlan(@RequestBody DevelopmentActivityToAddDto developmentActivityToAddDto) {
        DevelopmentActivityToAddDto developmentActivitySaved = developmentActivityService.addDevelopmentActivityForUserAndPlan(developmentActivityToAddDto);
        return ResponseEntity.status(HttpStatus.OK).body(developmentActivitySaved);
    }

    @PutMapping
    public ResponseEntity<DevelopmentActivityToEditDto> updateDevelopmentActivityForUserAndPlan(@RequestBody DevelopmentActivityToEditDto developmentActivityToEditDto) {
        DevelopmentActivityToEditDto updatedDevelopmentActivity = developmentActivityService.updateDevelopmentActivityForUserAndPlan(developmentActivityToEditDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDevelopmentActivity);
    }

    @PutMapping("/status")
    public ResponseEntity<DevelopmentActivityDto> changeDevelopmentActivityStatusById(@RequestBody ChangeActivityStatusForUserDto status) {
        DevelopmentActivityDto editedDevelopmentActivity = developmentActivityService.changeDevelopmentActivityStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(editedDevelopmentActivity);
    }

    @DeleteMapping("/{developmentActivityId}")
    public ResponseEntity<DevelopmentActivityDto> removeDevelopmentActivityById(@PathVariable Long developmentActivityId) {
        developmentActivityService.removeDevelopmentActivityById(developmentActivityId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/period")
    public ResponseEntity<List<DevelopmentActivityForUserDto>> getDevelopmentActivitiesByUserIdAndPeriod(@RequestBody UserWithPlanFromToDto userWithPlanFromToDto) {
        List<DevelopmentActivityForUserDto> developmentActivities = developmentActivityService
                .getDevelopmentActivitiesByUserIdAndDates(
                        userWithPlanFromToDto.getId(),
                        userWithPlanFromToDto.getDatePlanFrom(),
                        userWithPlanFromToDto.getDatePlanUntil());
        return ResponseEntity.status(HttpStatus.OK).body(developmentActivities);
    }

}
