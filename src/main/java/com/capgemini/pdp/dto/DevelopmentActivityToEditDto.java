package com.capgemini.pdp.dto;

public class DevelopmentActivityToEditDto {
    private Long developmentActivityId;
    private String comment;
    private String developmentAction;
    private String status;

    public Long getDevelopmentActivityId() {
        return developmentActivityId;
    }

    public void setDevelopmentActivityId(Long developmentActivityId) {
        this.developmentActivityId = developmentActivityId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDevelopmentAction() {
        return developmentAction;
    }

    public void setDevelopmentAction(String developmentAction) {
        this.developmentAction = developmentAction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
