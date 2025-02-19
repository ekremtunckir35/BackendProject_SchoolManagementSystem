package com.ekrem.school_management_system.repository.user;

import com.ekrem.school_management_system.entity.concretes.user.UserRole;
import com.ekrem.school_management_system.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT r FROM UserRole r WHERE r.roleType = ?1")
    Optional<UserRole> findByUserRoleType(RoleType roleType);


    Long id(Long id);
}
