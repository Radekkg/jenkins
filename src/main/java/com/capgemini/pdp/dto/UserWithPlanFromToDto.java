package com.capgemini.pdp.dto;

public class UserWithPlanFromToDto {
    private Long id;
    private Integer datePlanFrom;
    private Integer datePlanUntil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDatePlanFrom() {
        return datePlanFrom;
    }

    public void setDatePlanFrom(Integer datePlanFrom) {
        this.datePlanFrom = datePlanFrom;
    }

    public Integer getDatePlanUntil() {
        return datePlanUntil;
    }

    public void setDatePlanUntil(Integer datePlanUntil) {
        this.datePlanUntil = datePlanUntil;
    }
}
