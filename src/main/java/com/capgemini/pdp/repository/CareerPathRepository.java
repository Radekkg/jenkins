package com.capgemini.pdp.repository;

import com.capgemini.pdp.domain.CareerPathEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerPathRepository extends JpaRepository<CareerPathEntity, Long> {

    CareerPathEntity findByCareerPathName(String careerPathName);
}
