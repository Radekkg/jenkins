package com.capgemini.pdp.dto;

public class ChangeCurrentCareerPathForUserDto {
    private Long userId;
    private String carrierPathName;
    private String positionName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCarrierPathName() {
        return carrierPathName;
    }

    public void setCarrierPathName(String carrierPathName) {
        this.carrierPathName = carrierPathName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
