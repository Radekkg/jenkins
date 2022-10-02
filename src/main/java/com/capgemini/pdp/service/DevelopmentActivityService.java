package com.capgemini.pdp.service;

import com.capgemini.pdp.dto.ChangeActivityStatusForUserDto;
import com.capgemini.pdp.dto.DevelopmentActivityDto;
import com.capgemini.pdp.dto.DevelopmentActivityForUserDto;
import com.capgemini.pdp.dto.DevelopmentActivityToAddDto;
import com.capgemini.pdp.dto.DevelopmentActivityToEditDto;

import java.util.List;

public interface DevelopmentActivityService {

    DevelopmentActivityDto getDevelopmentActivityById(Long id);

    DevelopmentActivityToEditDto updateDevelopmentActivityForUserAndPlan(DevelopmentActivityToEditDto developmentActivityToEditDto);

    void removeDevelopmentActivityById(Long id);

    List<DevelopmentActivityDto> getAllDevelopmentActivities();

    List<DevelopmentActivityForUserDto> getDevelopmentActivitiesByUserIdAndDates(Long id, Integer datePlanFrom, Integer datePlanUntil);

    DevelopmentActivityToAddDto addDevelopmentActivityForUserAndPlan(DevelopmentActivityToAddDto developmentActivityToAddDto);

    DevelopmentActivityDto changeDevelopmentActivityStatus(ChangeActivityStatusForUserDto activityStatus);
}
