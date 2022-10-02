package com.capgemini.pdp.domain;

import javax.persistence.*;

@Entity
@Table(name = "position")
public class PositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "position_name")
    private String positionName;

    @ManyToOne
    private CareerPathEntity careerPath;

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

    public CareerPathEntity getCareerPath() {
        return careerPath;
    }

    public void setCareerPath(CareerPathEntity careerPath) {
        this.careerPath = careerPath;
    }
}
