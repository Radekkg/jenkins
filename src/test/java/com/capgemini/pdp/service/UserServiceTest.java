package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.CareerPathEntity;
import com.capgemini.pdp.domain.PositionEntity;
import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.ChangeCurrentCareerPathForUserDto;
import com.capgemini.pdp.dto.UserInfoDto;
import com.capgemini.pdp.repository.CareerPathRepository;
import com.capgemini.pdp.repository.PositionRepository;
import com.capgemini.pdp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private CareerPathRepository careerPathRepository;

    private static int generator;
    private UserEntity userAdded;
    private UserEntity supervisorAdded1;
    private UserEntity supervisorAdded2;

    @BeforeEach
    void setUp() {
        CareerPathEntity addedCareerPath = careerPathRepository.save(createExampleCareerPath());

        PositionEntity addedPosition = positionRepository.save(createExamplePosition(addedCareerPath));

        UserEntity manager1 = createExampleUser();
        manager1.setRoleName(UserRole.MANAGER);
        supervisorAdded1 = userRepository.save(manager1);

        UserEntity manager2 = createExampleUser();
        manager2.setRoleName(UserRole.MANAGER);
        supervisorAdded2 = userRepository.save(manager2);

        UserEntity employee = createExampleUser();
        employee.setRoleName(UserRole.USER);
        employee.setSupervisor(supervisorAdded1);
        employee.setPosition(addedPosition);
        userAdded = userRepository.save(employee);
    }

    @Test
    void shouldShowUserInfoByUserId() {
        //given
        String positionName = userAdded.getPosition().getPositionName();
        String careerPath = userAdded.getPosition().getCareerPath().getCareerPathName();
        String[] currentCarrierPath = {careerPath, positionName};

        //when
        UserInfoDto actual = userService.getUserInfo(userAdded.getId());

        //then
        assertEquals(userAdded.getFirstName(), actual.getUserFirstName());
        assertEquals(userAdded.getLastName(), actual.getUserLastName());
        assertEquals(supervisorAdded1.getFirstName(), actual.getSupervisorFirstName());
        assertEquals(supervisorAdded1.getLastName(), actual.getSupervisorLastName());
        assertEquals(supervisorAdded1.getEmail(), actual.getSupervisorEmail());
        assertEquals(userAdded.getCareerGoal(), actual.getCareerGoal());
        assertArrayEquals(currentCarrierPath, actual.getCurrentCareerPath());
    }

    @Test
    void shouldThrowExceptionWhenProvidedUserDoesNotExist() {
        //given
        Long userId = -1L;

        //when
        //then
        assertThrows(NoSuchElementException.class, () -> userService.getUserInfo(userId));
    }

    @Test
    void shouldReturnEmptyStringWhenProvidedUserDoesNotHaveSupervisor() {
        //given
        userAdded.setSupervisor(null);
        userRepository.save(userAdded);

        //when
        UserInfoDto actual = userService.getUserInfo(userAdded.getId());

        //then
        assertEquals("", actual.getSupervisorFirstName());
        assertEquals("", actual.getSupervisorLastName());
    }

    @Test
    void shouldReturnEmptyStringWhenProvidedUserDoesNotHavePosition() {
        //given
        userAdded.setPosition(null);
        userRepository.save(userAdded);

        //when
        UserInfoDto actual = userService.getUserInfo(userAdded.getId());

        //then
        assertArrayEquals(new String[]{"", ""}, actual.getCurrentCareerPath());
    }

    @Test
    void shouldShowEditedUserInfo() {
        //given
        UserInfoDto userToEdit = UserInfoDto.builder()
                .id(userAdded.getId())
                .userFirstName(userAdded.getFirstName())
                .userLastName("Kowalski")
                .supervisorEmail(supervisorAdded2.getEmail())
                .careerGoal(userAdded.getCareerGoal())
                .build();

        //when
        UserInfoDto actual = userService.editUserProfile(userToEdit);

        //then
        assertEquals(userToEdit.getUserFirstName(), actual.getUserFirstName());
        assertEquals(userToEdit.getUserLastName(), actual.getUserLastName());
        assertEquals(supervisorAdded2.getFirstName(), actual.getSupervisorFirstName());
        assertEquals(supervisorAdded2.getLastName(), actual.getSupervisorLastName());
        assertEquals(supervisorAdded2.getEmail(), actual.getSupervisorEmail());
        assertEquals(userToEdit.getCareerGoal(), actual.getCareerGoal());
    }

    @Test
    void shouldThrowExceptionWhenProvidedSupervisorEmailDoesNotExist() {
        //given
        UserInfoDto userToEdit = UserInfoDto.builder()
                .id(userAdded.getId())
                .userFirstName(userAdded.getFirstName())
                .userLastName("Kowalski")
                .supervisorEmail("k.kowalski@ac.cd")
                .careerGoal(userAdded.getCareerGoal())
                .build();

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> userService.editUserProfile(userToEdit));
    }

    @Test
    void shouldThrowExceptionWhenEmployeeWithProvidedSupervisorEmailDoesNotHaveManagerRole() {
        //given
        UserEntity employee = createExampleUser();
        employee.setRoleName(UserRole.USER);
        UserEntity employeeAdded = userRepository.save(employee);

        UserInfoDto userToEdit = UserInfoDto.builder()
                .id(userAdded.getId())
                .userFirstName(userAdded.getFirstName())
                .userLastName("Kowalski")
                .supervisorEmail(employeeAdded.getEmail())
                .careerGoal(userAdded.getCareerGoal())
                .build();

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> userService.editUserProfile(userToEdit));
    }

    @Test
    void shouldChangeCurrentCareerPath() {
        //given
        ChangeCurrentCareerPathForUserDto newCareerPath = new ChangeCurrentCareerPathForUserDto();
        newCareerPath.setUserId(userAdded.getId());
        newCareerPath.setCarrierPathName("Business Technology");
        newCareerPath.setPositionName("Business Analyst");
        //when
        UserInfoDto editedUser = userService.changeCurrentCareerPath(newCareerPath);
        //then
        assertEquals(editedUser.getCurrentCareerPath()[0], newCareerPath.getCarrierPathName());
        assertEquals(editedUser.getCurrentCareerPath()[1], newCareerPath.getPositionName());
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

    private CareerPathEntity createExampleCareerPath() {
        CareerPathEntity careerPathEntity = new CareerPathEntity();
        careerPathEntity.setCareerPathName("name" + generator++);
        return careerPathEntity;
    }

    private PositionEntity createExamplePosition(CareerPathEntity careerPathAdded) {
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setPositionName("name" + generator++);
        positionEntity.setCareerPath(careerPathAdded);
        return positionEntity;
    }
}