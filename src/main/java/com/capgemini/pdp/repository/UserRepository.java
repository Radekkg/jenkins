package com.capgemini.pdp.repository;

import com.capgemini.pdp.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    List<UserEntity> findAllBySupervisorId(Long supervisorId);

    @Query("SELECT u FROM UserEntity u JOIN u.position p WHERE p.positionName = :positionName")
    List<UserEntity> findAllByPositionName(@Param("positionName") String positionName);
}
