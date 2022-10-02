package com.capgemini.pdp.service;

import com.capgemini.pdp.domain.*;
import com.capgemini.pdp.domain.enumerated.ActivityStatus;
import com.capgemini.pdp.domain.enumerated.UserRole;
import com.capgemini.pdp.dto.ChangeActivityStatusForUserDto;
import com.capgemini.pdp.dto.DevelopmentActivityForUserDto;
import com.capgemini.pdp.dto.DevelopmentActivityToAddDto;
import com.capgemini.pdp.dto.DevelopmentActivityToEditDto;
import com.capgemini.pdp.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class DevelopmentActivityServiceTest {
    @Autowired
    private DevelopmentActivityService developmentActivityService;
    @Autowired
    private DevelopmentActivityRepository developmentActivityRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private CareerPathRepository careerPathRepository;
    @Autowired
    private CompetencyRepository competencyRepository;
    @Autowired
    private TypeRepository typeRepository;

    private static int generator;
    private UserEntity userAdded1;
    private PlanEntity planAdded2018WithoutActivities;
    private PlanEntity planAdded2020WithActivities;
    private PlanEntity planAdded2019WithActivities;
    private PlanEntity planAddedCurrentYearWithActivities;
    private DevelopmentActivityEntity developmentActivityAddedFor2020;
    private DevelopmentActivityEntity developmentActivityAddedFor2019;
    private DevelopmentActivityEntity developmentActivityAddedForCurrentYear;
    private TypeEntity typeFromDataBase;
    private int currentYear;

    @BeforeEach
    void setUp() {
        currentYear = LocalDate.now().getYear();

        CareerPathEntity careerPathAdded1 = careerPathRepository.save(createExampleCareerPath());
        PositionEntity positionAdded1 = positionRepository.save(createExamplePosition(careerPathAdded1));
        CompetencyEntity competencyAdded1 = competencyRepository.save(createExampleCompetency(positionAdded1));
        typeFromDataBase = typeRepository.getById(1L);

        UserEntity userEntity = createExampleUser();
        userEntity.setRoleName(UserRole.USER);
        userEntity.setPosition(positionAdded1);
        userAdded1 = userRepository.save(userEntity);

        PlanEntity examplePlan2018 = createExamplePlan(userAdded1);
        examplePlan2018.setYear(2018);
        planAdded2018WithoutActivities = planRepository.save(examplePlan2018);

        PlanEntity examplePlan2019 = createExamplePlan(userAdded1);
        examplePlan2019.setYear(2019);
        planAdded2019WithActivities = planRepository.save(examplePlan2019);
        developmentActivityAddedFor2019 = developmentActivityRepository.save(createDevelopmentActivity(planAdded2019WithActivities, competencyAdded1, typeFromDataBase));

        PlanEntity examplePlan2020 = createExamplePlan(userAdded1);
        examplePlan2020.setYear(2020);
        planAdded2020WithActivities = planRepository.save(examplePlan2020);
        developmentActivityAddedFor2020 = developmentActivityRepository.save(createDevelopmentActivity(planAdded2020WithActivities, competencyAdded1, typeFromDataBase));

        PlanEntity examplePlanCurrentYear = createExamplePlan(userAdded1);
        examplePlanCurrentYear.setYear(currentYear);
        planAddedCurrentYearWithActivities = planRepository.save(examplePlanCurrentYear);
        developmentActivityAddedForCurrentYear = developmentActivityRepository.save(createDevelopmentActivity(planAddedCurrentYearWithActivities, competencyAdded1, typeFromDataBase));

    }

    @Test
    void shouldGetDevelopmentActivitiesForCurrentYearByUserIdWhenFromUntilDatesAreNull() {
        //given
        Long userId = userAdded1.getId();
        //when
        List<DevelopmentActivityForUserDto> developmentActivities = developmentActivityService
                .getDevelopmentActivitiesByUserIdAndDates(userId, null, null);
        //then
        assertEquals(1, developmentActivities.size());
    }

    @Test
    void shouldNotGetDevelopmentActivitiesByUserIdWhenUserDoesNotHaveAnyForThePeriod() {
        //given
        int datePlanFrom = 2018;
        int datePlanUntil = 2018;
        Long userId = userAdded1.getId();
        //when
        List<DevelopmentActivityForUserDto> developmentActivities = developmentActivityService.getDevelopmentActivitiesByUserIdAndDates(userId, datePlanFrom, datePlanUntil);
        //then
        assertEquals(0, developmentActivities.size());
    }

    @Test
    void shouldGetDevelopmentActivitiesByUserIdWhenUserHasActivitiesForThePeriod() {
        //given
        int datePlanFrom = 2019;
        int datePlanUntil = 2020;
        Long userId = userAdded1.getId();
        //when
        List<DevelopmentActivityForUserDto> developmentActivities = developmentActivityService.getDevelopmentActivitiesByUserIdAndDates(userId, datePlanFrom, datePlanUntil);
        //then
        assertEquals(2, developmentActivities.size());
    }

    @Test
    void shouldGetDevelopmentActivitiesByUserIdWhenUserHasActivitiesForThePeriodSortedByDeadline() {
        //given
        int datePlanFrom = 2019;
        int datePlanUntil = 2020;
        Long userId = userAdded1.getId();
        //when
        List<DevelopmentActivityForUserDto> developmentActivities = developmentActivityService.getDevelopmentActivitiesByUserIdAndDates(userId, datePlanFrom, datePlanUntil);
        //then
        assertEquals(2, developmentActivities.size());
        assertEquals(2019, developmentActivities.get(0).getDeadline());
        assertEquals(2020, developmentActivities.get(1).getDeadline());
    }

    @Test
    void shouldAddDevelopmentActivityForUserAndPlan() {
        //given
        DevelopmentActivityToAddDto developmentActivityToAddDto = DevelopmentActivityToAddDto.builder()
                .developmentAction("TEST")
                .comment("TEST")
                .deadline(1)
                .competencyId(1L)
                .userId(1L)
                .typeId(1L)
                .build();
        //when
        DevelopmentActivityToAddDto added = developmentActivityService.addDevelopmentActivityForUserAndPlan(developmentActivityToAddDto);
        List<DevelopmentActivityForUserDto> developmentActivityDtos = developmentActivityService.getDevelopmentActivitiesByUserIdAndDates(1L, 1, 1);
        DevelopmentActivityEntity developmentActivityEntity = developmentActivityRepository.findById(developmentActivityDtos.get(0).getId()).orElse(null);
        //then
        assertEquals("TEST", added.getDevelopmentAction());
        assert developmentActivityEntity != null;
        assertEquals(1L, developmentActivityEntity.getPlan().getUser().getId());
    }

    @Test
    void shouldThrowWhenAddDevelopmentActivityForUserInvalidId() {
        //given
        DevelopmentActivityToAddDto developmentActivityToAddDto = DevelopmentActivityToAddDto.builder()
                .developmentAction("TEST")
                .comment("TEST")
                .deadline(1)
                .competencyId(1L)
                .userId(-1L)
                .typeId(1L)
                .build();
        //then
        assertThrows(NoSuchElementException.class, () -> developmentActivityService.addDevelopmentActivityForUserAndPlan(developmentActivityToAddDto));
    }

    @Test
    void shouldChangeDevelopmentActivityStatus() {
        //given
        Long id = developmentActivityAddedFor2020.getId();
        String oldStatus = developmentActivityAddedFor2020.getActivityStatus().toString();
        String newStatus = "in_progress";

        ChangeActivityStatusForUserDto activityStatus = new ChangeActivityStatusForUserDto();
        activityStatus.setActivityId(id);
        activityStatus.setStatus(newStatus);

        //when
        developmentActivityService.changeDevelopmentActivityStatus(activityStatus);
        String updatedStatus = developmentActivityAddedFor2020.getActivityStatus().toString();
        //then
        assertNotEquals(oldStatus, updatedStatus);
        assertEquals(newStatus.toUpperCase(), updatedStatus);
    }

    @Test
    void shouldNotChangeStatusAndThrowExceptionWhenStatusIsNotValid() {
        //given
        Long id = developmentActivityAddedFor2020.getId();
        String status = "abc";

        ChangeActivityStatusForUserDto activityStatus = new ChangeActivityStatusForUserDto();
        activityStatus.setActivityId(id);
        activityStatus.setStatus(status);

        //then
        assertThrows(IllegalArgumentException.class, () -> developmentActivityService.changeDevelopmentActivityStatus(activityStatus));
    }

    @Test
    void shouldEditDevelopmentActivityForUserAndPlan() {
        //given
        DevelopmentActivityToEditDto developmentActivityToEditDto = new DevelopmentActivityToEditDto();
        developmentActivityToEditDto.setDevelopmentActivityId(1L);
        developmentActivityToEditDto.setDevelopmentAction("TEST");
        developmentActivityToEditDto.setComment("TEST");
        developmentActivityToEditDto.setStatus("FINISHED");

        //when
        developmentActivityService.updateDevelopmentActivityForUserAndPlan(developmentActivityToEditDto);
        DevelopmentActivityEntity developmentActivityEntity = developmentActivityRepository.findById(1L).orElse(null);
        //then
        assert developmentActivityEntity != null;
        assertEquals("TEST", developmentActivityEntity.getDevelopmentAction());
        assertEquals("TEST", developmentActivityEntity.getComment());
    }

    @Test
    void shouldThrowWhenEditDevelopmentActivityForUserIdInvalid() {
        //given
        DevelopmentActivityToEditDto developmentActivityToEditDto = new DevelopmentActivityToEditDto();
        developmentActivityToEditDto.setDevelopmentActivityId(-1L);
        developmentActivityToEditDto.setDevelopmentAction("TEST");
        developmentActivityToEditDto.setComment("TEST");
        //then
        assertThrows(NoSuchElementException.class, () -> developmentActivityService.updateDevelopmentActivityForUserAndPlan(developmentActivityToEditDto));
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

    private CompetencyEntity createExampleCompetency(PositionEntity positionAdded) {
        CompetencyEntity competencyEntity = new CompetencyEntity();
        competencyEntity.setDescription("description" + generator++);
        competencyEntity.setPosition(positionAdded);
        return competencyEntity;
    }

    private PlanEntity createExamplePlan(UserEntity userEntity) {
        PlanEntity planEntity = new PlanEntity();
        planEntity.setYear(2000 + generator++);
        planEntity.setUser(userEntity);
        return planEntity;
    }

    private DevelopmentActivityEntity createDevelopmentActivity(PlanEntity planAdded, CompetencyEntity competencyAdded, TypeEntity typeAdded) {
        DevelopmentActivityEntity developmentActivityEntity = new DevelopmentActivityEntity();
        developmentActivityEntity.setDevelopmentAction("action" + generator++);
        developmentActivityEntity.setActivityStatus(ActivityStatus.CANCELLED);
        developmentActivityEntity.setPlan(planAdded);
        developmentActivityEntity.setCompetency(competencyAdded);
        developmentActivityEntity.setType(typeAdded);
        return developmentActivityEntity;
    }

}