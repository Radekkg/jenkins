package com.capgemini.pdp.controller;

import com.capgemini.pdp.dto.UserForManagerAndAdminViewDto;
import com.capgemini.pdp.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/employee/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<List<UserForManagerAndAdminViewDto>> getAllUsersByManagerId(@PathVariable Long managerId) {
        List<UserForManagerAndAdminViewDto> usersByManagerId = managerService.getUsersByManagerId(managerId);
        return ResponseEntity.status(HttpStatus.OK).body(usersByManagerId);
    }

}
