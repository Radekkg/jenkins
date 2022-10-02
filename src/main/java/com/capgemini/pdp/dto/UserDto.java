package com.capgemini.pdp.dto;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String roleName;
    private Long supervisorId;
    private Long positionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    public static final class UserDtoBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String roleName;
        private Long supervisorId;
        private Long positionId;

        private UserDtoBuilder() {
        }

        public UserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDtoBuilder roleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public UserDtoBuilder supervisorId(Long supervisorId) {
            this.supervisorId = supervisorId;
            return this;
        }

        public UserDtoBuilder positionId(Long positionId) {
            this.positionId = positionId;
            return this;
        }

        public UserDto build() {
            UserDto userDto = new UserDto();
            userDto.setId(id);
            userDto.setFirstName(firstName);
            userDto.setLastName(lastName);
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setRoleName(roleName);
            userDto.setSupervisorId(supervisorId);
            userDto.setPositionId(positionId);
            return userDto;
        }
    }
}
