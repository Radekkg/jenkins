package com.capgemini.pdp.repository;

import com.capgemini.pdp.domain.DevelopmentActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevelopmentActivityRepository extends JpaRepository<DevelopmentActivityEntity, Long> {

    @Query("SELECT d FROM DevelopmentActivityEntity d JOIN d.plan p JOIN p.user u WHERE u.id = :userId AND p.year >= :datePlanFrom AND p.year <= :datePlanUntil ORDER BY p.year ASC")
    List<DevelopmentActivityEntity> findDevelopmentActivitiesByUserIdAndDates(@Param("userId") Long userId,
                                                                              @Param("datePlanFrom") Integer datePlanFrom, @Param("datePlanUntil") Integer datePlanUntil);
}
