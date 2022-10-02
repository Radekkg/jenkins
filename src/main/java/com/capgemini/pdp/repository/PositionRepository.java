package com.capgemini.pdp.repository;

import com.capgemini.pdp.domain.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    PositionEntity findByPositionName(String name);

    List<PositionEntity> findAllByCareerPathId(Long careerPathId);


}
