package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.UserForManagerAndAdminViewDto;
import com.capgemini.pdp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ManagerServiceTest {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserRepository userRepository;

    private static int generator = 0;
    private UserEntity savedManager1;
    private UserEntity savedManager2;
    private UserEntity savedManager3;
    private UserEntity savedEmployee11;
    private UserEntity savedEmployee12;
    private UserEntity savedEmployee21;
    private UserEntity savedEmployee22;


    @BeforeEach
    void setUp() {

        UserEntity manager1 = createExampleUser();
        manager1.setRoleName(UserRole.MANAGER);
        savedManager1 = userRepository.save(manager1);

        UserEntity manager2 = createExampleUser();
        manager2.setRoleName(UserRole.MANAGER);
        savedManager2 = userRepository.save(manager2);

        UserEntity manager3 = createExampleUser();
        manager3.setRoleName(UserRole.MANAGER);
        savedManager3 = userRepository.save(manager3);

        UserEntity employee11 = createExampleUser();
        employee11.setRoleName(UserRole.USER);
        employee11.setSupervisor(savedManager1);
        savedEmployee11 = userRepository.save(employee11);

        UserEntity employee12 = createExampleUser();
        employee12.setRoleName(UserRole.USER);
        employee12.setSupervisor(savedManager1);
        savedEmployee12 = userRepository.save(employee12);

        UserEntity employee21 = createExampleUser();
        employee21.setRoleName(UserRole.USER);
        employee21.setSupervisor(savedManager2);
        savedEmployee21 = userRepository.save(employee21);

        UserEntity employee22 = createExampleUser();
        employee22.setRoleName(UserRole.USER);
        employee22.setSupervisor(savedManager2);
        savedEmployee22 = userRepository.save(employee22);
    }

    @Test
    void shouldReturnAllUsersByManagerId() {
        //given
        //when
        List<UserForManagerAndAdminViewDto> usersByManagerId = managerService.getUsersByManagerId(savedManager1.getId());
        //then
        assertEquals(2, usersByManagerId.size());
    }

    @Test
    void shouldReturnEmptyListTryingFindUsersByManagerIdWhenTheManagerDoesNotHaveAnyEmployees() {
        //given
        //when
        List<UserForManagerAndAdminViewDto> usersByManagerId = managerService.getUsersByManagerId(savedManager3.getId());
        //then
        assertEquals(0, usersByManagerId.size());
    }

    private UserEntity createExampleUser() {
        UserEntity user = new UserEntity();
        generator++;
        user.setFirstName("name" + generator);
        user.setLastName("lastname" + generator);
        user.setPassword("password" + generator);
        user.setEmail(generator + "employee@gmail.com");
        return user;
    }
}