package com.capgemini.pdp.dto;

public class DevelopmentActivityToAddDto {
    private Long userId;
    private Integer deadline;
    private String developmentAction;
    private Long typeId;
    private Long competencyId;
    private String comment;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public String getDevelopmentAction() {
        return developmentAction;
    }

    public void setDevelopmentAction(String developmentAction) {
        this.developmentAction = developmentAction;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getCompetencyId() {
        return competencyId;
    }

    public void setCompetencyId(Long competencyId) {
        this.competencyId = competencyId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static DevelopmentActivityToAddDtoBuilder builder() {
        return new DevelopmentActivityToAddDtoBuilder();
    }

    public static final class DevelopmentActivityToAddDtoBuilder {
        private Long userId;
        private Integer deadline;
        private String developmentAction;
        private Long typeId;
        private Long competencyId;
        private String comment;

        private DevelopmentActivityToAddDtoBuilder() {
        }

        public DevelopmentActivityToAddDtoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public DevelopmentActivityToAddDtoBuilder deadline(Integer deadline) {
            this.deadline = deadline;
            return this;
        }

        public DevelopmentActivityToAddDtoBuilder developmentAction(String developmentAction) {
            this.developmentAction = developmentAction;
            return this;
        }

        public DevelopmentActivityToAddDtoBuilder typeId(Long typeId) {
            this.typeId = typeId;
            return this;
        }

        public DevelopmentActivityToAddDtoBuilder competencyId(Long competencyId) {
            this.competencyId = competencyId;
            return this;
        }

        public DevelopmentActivityToAddDtoBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public DevelopmentActivityToAddDto build() {
            DevelopmentActivityToAddDto developmentActivityToAddDto = new DevelopmentActivityToAddDto();
            developmentActivityToAddDto.setUserId(userId);
            developmentActivityToAddDto.setDeadline(deadline);
            developmentActivityToAddDto.setDevelopmentAction(developmentAction);
            developmentActivityToAddDto.setTypeId(typeId);
            developmentActivityToAddDto.setCompetencyId(competencyId);
            developmentActivityToAddDto.setComment(comment);
            return developmentActivityToAddDto;
        }
    }
}
