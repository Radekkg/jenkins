package com.capgemini.pdp.dto;

public class DevelopmentActivityForUserDto {
    private Long id;
    private String developmentAction;
    private String activityStatus;
    private String comment;
    private String type;
    private String competencyName;
    private int deadline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevelopmentAction() {
        return developmentAction;
    }

    public void setDevelopmentAction(String developmentAction) {
        this.developmentAction = developmentAction;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompetencyName() {
        return competencyName;
    }

    public void setCompetencyName(String competencyName) {
        this.competencyName = competencyName;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public static DevelopmentActivityForUserDtoBuilder builder() {
        return new DevelopmentActivityForUserDtoBuilder();
    }

    public static final class DevelopmentActivityForUserDtoBuilder {
        private Long id;
        private String developmentAction;
        private String activityStatus;
        private String comment;
        private String type;
        private String competencyName;
        private int deadline;

        private DevelopmentActivityForUserDtoBuilder() {
        }

        public DevelopmentActivityForUserDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DevelopmentActivityForUserDtoBuilder developmentAction(String developmentAction) {
            this.developmentAction = developmentAction;
            return this;
        }

        public DevelopmentActivityForUserDtoBuilder activityStatus(String activityStatus) {
            this.activityStatus = activityStatus;
            return this;
        }

        public DevelopmentActivityForUserDtoBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public DevelopmentActivityForUserDtoBuilder type(String type) {
            this.type = type;
            return this;
        }

        public DevelopmentActivityForUserDtoBuilder competencyName(String competencyName) {
            this.competencyName = competencyName;
            return this;
        }

        public DevelopmentActivityForUserDtoBuilder deadline(int deadline) {
            this.deadline = deadline;
            return this;
        }

        public DevelopmentActivityForUserDto build() {
            DevelopmentActivityForUserDto developmentActivityForUserDto = new DevelopmentActivityForUserDto();
            developmentActivityForUserDto.setId(id);
            developmentActivityForUserDto.setDevelopmentAction(developmentAction);
            developmentActivityForUserDto.setActivityStatus(activityStatus);
            developmentActivityForUserDto.setComment(comment);
            developmentActivityForUserDto.setType(type);
            developmentActivityForUserDto.setCompetencyName(competencyName);
            developmentActivityForUserDto.setDeadline(deadline);
            return developmentActivityForUserDto;
        }
    }
}
