package com.projectmanagement.projecttaskmanagementsystem.repository;

import com.projectmanagement.projecttaskmanagementsystem.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    List<AppUser> findByOwnerAdmin_Id(Long adminId);

    boolean existsByUsername(String username);

    long countByRole(String role);

    List<AppUser> findByRole(String role);

    List<AppUser> findByRoleAndTeamMemberIsNotNull(String role);
}