package com.capgemini.pdp.dto;

public class UserForManagerAndAdminViewDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String careerPath;
    private String position;
    private String role;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCareerPath() {
        return careerPath;
    }

    public String getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }

    public static UserForManagerAndAdminViewDtoBuilder builder() {
        return new UserForManagerAndAdminViewDtoBuilder();
    }

    public static final class UserForManagerAndAdminViewDtoBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String careerPath;
        private String position;
        private String role;

        private UserForManagerAndAdminViewDtoBuilder() {
        }

        public UserForManagerAndAdminViewDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserForManagerAndAdminViewDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserForManagerAndAdminViewDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserForManagerAndAdminViewDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserForManagerAndAdminViewDtoBuilder careerPath(String careerPath) {
            this.careerPath = careerPath;
            return this;
        }

        public UserForManagerAndAdminViewDtoBuilder position(String position) {
            this.position = position;
            return this;
        }

        public UserForManagerAndAdminViewDtoBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserForManagerAndAdminViewDto build() {
            UserForManagerAndAdminViewDto userForManagerAndAdminViewDto = new UserForManagerAndAdminViewDto();
            userForManagerAndAdminViewDto.firstName = this.firstName;
            userForManagerAndAdminViewDto.email = this.email;
            userForManagerAndAdminViewDto.position = this.position;
            userForManagerAndAdminViewDto.id = this.id;
            userForManagerAndAdminViewDto.role = this.role;
            userForManagerAndAdminViewDto.lastName = this.lastName;
            userForManagerAndAdminViewDto.careerPath = this.careerPath;
            return userForManagerAndAdminViewDto;
        }
    }
}
