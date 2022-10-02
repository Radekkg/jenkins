package com.capgemini.pdp.mapper;

import com.capgemini.pdp.domain.UserEntity;
import com.capgemini.pdp.dto.UserInfoDto;

public class UserInfoMapper {

    private UserInfoMapper() {
    }

    public static UserInfoDto mapToDto(UserEntity user) {
        String careerPathName = "";
        String positionName = "";
        if (user.getPosition() != null) {
            careerPathName = user.getPosition().getCareerPath().getCareerPathName();
            positionName = user.getPosition().getPositionName();
        }
        String supervisorFirstName = "";
        String supervisorLastName = "";
        String supervisorEmail = "";
        if (user.getSupervisor() != null) {
            supervisorFirstName = user.getSupervisor().getFirstName();
            supervisorLastName = user.getSupervisor().getLastName();
            supervisorEmail = user.getSupervisor().getEmail();
        }
        return UserInfoDto.builder()
                .userFirstName(user.getFirstName())
                .userLastName(user.getLastName())
                .supervisorFirstName(supervisorFirstName)
                .supervisorLastName(supervisorLastName)
                .supervisorEmail(supervisorEmail)
                .careerGoal(user.getCareerGoal())
                .currentCareerPath(new String[] {careerPathName, positionName})
                .build();
    }
}
