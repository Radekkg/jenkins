package com.capgemini.pdp.dto;

import com.capgemini.pdp.domain.enumerated.ActivityStatus;

public class DevelopmentActivityDto {
    private Long id;
    private String developmentAction;
    private ActivityStatus activityStatus;
    private String comment;
    private Long typeId;
    private Long competencyId;
    private Long planId;

    public Long getId() {
        return id;
    }

    public String getDevelopmentAction() {
        return developmentAction;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public String getComment() {
        return comment;
    }

    public Long getTypeId() {
        return typeId;
    }

    public Long getCompetencyId() {
        return competencyId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDevelopmentAction(String developmentAction) {
        this.developmentAction = developmentAction;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public void setCompetencyId(Long competencyId) {
        this.competencyId = competencyId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public static DevelopmentActivityDtoBuilder builder() {
        return new DevelopmentActivityDtoBuilder();
    }

    public static final class DevelopmentActivityDtoBuilder {
        private Long id;
        private String developmentAction;
        private ActivityStatus activityStatus;
        private String comment;
        private Long typeId;
        private Long competencyId;
        private Long planId;

        private DevelopmentActivityDtoBuilder() {
        }

        public DevelopmentActivityDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DevelopmentActivityDtoBuilder developmentAction(String developmentAction) {
            this.developmentAction = developmentAction;
            return this;
        }

        public DevelopmentActivityDtoBuilder activityStatus(ActivityStatus activityStatus) {
            this.activityStatus = activityStatus;
            return this;
        }

        public DevelopmentActivityDtoBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public DevelopmentActivityDtoBuilder typeId(Long typeId) {
            this.typeId = typeId;
            return this;
        }

        public DevelopmentActivityDtoBuilder competencyId(Long competencyId) {
            this.competencyId = competencyId;
            return this;
        }

        public DevelopmentActivityDtoBuilder planId(Long planId) {
            this.planId = planId;
            return this;
        }

        public DevelopmentActivityDto build() {
            DevelopmentActivityDto developmentActivityDto = new DevelopmentActivityDto();
            developmentActivityDto.setId(id);
            developmentActivityDto.setDevelopmentAction(developmentAction);
            developmentActivityDto.setActivityStatus(activityStatus);
            developmentActivityDto.setComment(comment);
            developmentActivityDto.setTypeId(typeId);
            developmentActivityDto.setCompetencyId(competencyId);
            developmentActivityDto.setPlanId(planId);
            return developmentActivityDto;
        }
    }
}
