package com.capgemini.pdp.repository;

import com.capgemini.pdp.domain.CompetencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetencyRepository extends JpaRepository<CompetencyEntity, Long> {
    List<CompetencyEntity> findAllByPositionId(Long id);
}
