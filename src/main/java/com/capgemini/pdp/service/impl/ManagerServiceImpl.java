package com.capgemini.pdp.service.impl;

import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.dto.UserForManagerAndAdminViewDto;
import com.capgemini.pdp.mapper.UserForManagerAndAdminViewMapper;
import com.capgemini.pdp.repository.UserRepository;
import com.capgemini.pdp.service.ManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final UserRepository userRepository;

    public ManagerServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserForManagerAndAdminViewDto> getUsersByManagerId(Long managerId) {
        List<UserEntity> allByManager = userRepository.findAllBySupervisorId(managerId);
        return UserForManagerAndAdminViewMapper.mapToDtoList(allByManager);
    }
}
