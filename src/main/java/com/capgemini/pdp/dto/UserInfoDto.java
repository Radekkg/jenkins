package com.capgemini.pdp.dto;

public class UserInfoDto {
    private Long id;
    private String userFirstName;
    private String userLastName;
    private String supervisorFirstName;
    private String supervisorLastName;
    private String supervisorEmail;
    private String careerGoal;
    private String[] currentCareerPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getSupervisorFirstName() {
        return supervisorFirstName;
    }

    public void setSupervisorFirstName(String supervisorFirstName) {
        this.supervisorFirstName = supervisorFirstName;
    }

    public String getSupervisorLastName() {
        return supervisorLastName;
    }

    public void setSupervisorLastName(String supervisorLastName) {
        this.supervisorLastName = supervisorLastName;
    }

    public String getSupervisorEmail() {
        return supervisorEmail;
    }

    public void setSupervisorEmail(String supervisorEmail) {
        this.supervisorEmail = supervisorEmail;
    }

    public String getCareerGoal() {
        return careerGoal;
    }

    public void setCareerGoal(String careerGoal) {
        this.careerGoal = careerGoal;
    }

    public String[] getCurrentCareerPath() {
        return currentCareerPath;
    }

    public void setCurrentCareerPath(String[] currentCareerPath) {
        this.currentCareerPath = currentCareerPath;
    }

    public static UserInfoDtoBuilder builder() {
        return new UserInfoDtoBuilder();
    }

    public static final class UserInfoDtoBuilder {
        private Long id;
        private String userFirstName;
        private String userLastName;
        private String supervisorFirstName;
        private String supervisorLastName;
        private String supervisorEmail;
        private String careerGoal;
        private String[] currentCareerPath;

        private UserInfoDtoBuilder() {
        }

        public UserInfoDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserInfoDtoBuilder userFirstName(String userFirstName) {
            this.userFirstName = userFirstName;
            return this;
        }

        public UserInfoDtoBuilder userLastName(String userLastName) {
            this.userLastName = userLastName;
            return this;
        }

        public UserInfoDtoBuilder supervisorFirstName(String supervisorFirstName) {
            this.supervisorFirstName = supervisorFirstName;
            return this;
        }

        public UserInfoDtoBuilder supervisorLastName(String supervisorLastName) {
            this.supervisorLastName = supervisorLastName;
            return this;
        }

        public UserInfoDtoBuilder supervisorEmail(String supervisorEmail) {
            this.supervisorEmail = supervisorEmail;
            return this;
        }

        public UserInfoDtoBuilder careerGoal(String careerGoal) {
            this.careerGoal = careerGoal;
            return this;
        }

        public UserInfoDtoBuilder currentCareerPath(String[] currentCareerPath) {
            this.currentCareerPath = currentCareerPath;
            return this;
        }

        public UserInfoDto build() {
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setId(id);
            userInfoDto.setUserFirstName(userFirstName);
            userInfoDto.setUserLastName(userLastName);
            userInfoDto.setSupervisorFirstName(supervisorFirstName);
            userInfoDto.setSupervisorLastName(supervisorLastName);
            userInfoDto.setSupervisorEmail(supervisorEmail);
            userInfoDto.setCareerGoal(careerGoal);
            userInfoDto.setCurrentCareerPath(currentCareerPath);
            return userInfoDto;
        }
    }
}
