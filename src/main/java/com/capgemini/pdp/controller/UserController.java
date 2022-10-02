package com.capgemini.pdp.controller;

import com.capgemini.pdp.dto.ChangeCurrentCareerPathForUserDto;
import com.capgemini.pdp.dto.CompetencyForUserDto;
import com.capgemini.pdp.dto.UserInfoDto;
import com.capgemini.pdp.service.CompetencyService;
import com.capgemini.pdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CompetencyService competencyService;

    @Autowired
    public UserController(UserService userService, CompetencyService competencyService) {
        this.userService = userService;
        this.competencyService = competencyService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDto> getUserInfoById(@PathVariable Long userId) {
        UserInfoDto user = userService.getUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping
    public ResponseEntity<UserInfoDto> editUserProfile(@RequestBody UserInfoDto userInfo) {
        UserInfoDto editedUser = userService.editUserProfile(userInfo);
        return ResponseEntity.status(HttpStatus.OK).body(editedUser);
    }

    @PutMapping("/current_path")
    public ResponseEntity<UserInfoDto> changeUserCurrentCareerPath(@RequestBody ChangeCurrentCareerPathForUserDto currentCareerPath) {
        UserInfoDto editedUser = userService.changeCurrentCareerPath(currentCareerPath);
        return ResponseEntity.status(HttpStatus.OK).body(editedUser);
    }


    @GetMapping("/{userId}/competency")
    public ResponseEntity<List<CompetencyForUserDto>> getCompetencyByUserId(@PathVariable Long userId) {
        List<CompetencyForUserDto> competencyForUserDtos = competencyService.getCompetenciesAvailableForUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(competencyForUserDtos);
    }
}