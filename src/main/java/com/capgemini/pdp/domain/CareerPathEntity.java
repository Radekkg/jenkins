package com.capgemini.pdp.domain;

import javax.persistence.*;

@Entity
@Table(name = "career_path")
public class CareerPathEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "career_path_name")
    private String careerPathName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCareerPathName() {
        return careerPathName;
    }

    public void setCareerPathName(String careerPathName) {
        this.careerPathName = careerPathName;
    }
}
