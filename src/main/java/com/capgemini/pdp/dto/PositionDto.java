package com.capgemini.pdp.dto;

public class PositionDto {
    private Long id;
    private String positionName;
    private Long careerPathId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Long getCareerPathId() {
        return careerPathId;
    }

    public void setCareerPathId(Long careerPathId) {
        this.careerPathId = careerPathId;
    }
}
