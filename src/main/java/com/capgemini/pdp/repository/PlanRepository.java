package com.capgemini.pdp.repository;

import com.capgemini.pdp.domain.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long> {

    @Query("SELECT p FROM PlanEntity p JOIN p.user u WHERE u.id = :userId AND p.year = :deadline")
    PlanEntity findByUserIdAndDeadline(@Param("userId")Long userId, @Param("deadline")Integer deadline);
}
