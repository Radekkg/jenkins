package com.capgemini.pdp.service;

import com.capgemini.pdp.dto.UserForManagerAndAdminViewDto;

import java.util.List;

public interface ManagerService {

    List<UserForManagerAndAdminViewDto> getUsersByManagerId(Long managerId);
}
