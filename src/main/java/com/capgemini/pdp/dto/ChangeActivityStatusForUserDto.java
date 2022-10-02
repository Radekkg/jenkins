package com.capgemini.pdp.dto;

public class ChangeActivityStatusForUserDto {
    private Long activityId;
    private String status;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
