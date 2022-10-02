package com.capgemini.pdp.controller;

import com.capgemini.pdp.dto.CredentialsDto;
import com.capgemini.pdp.dto.LoggedInUserDto;
import com.capgemini.pdp.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoggedInUserDto> login(@RequestBody CredentialsDto credentialsDto) {
        LoggedInUserDto authInfo = loginService.login(credentialsDto);
        if (authInfo != null) {
            return ResponseEntity.status(HttpStatus.OK).body(authInfo);
        }
        throw new EntityNotFoundException("There is no user with email: " + credentialsDto.getEmail());

    }

}
