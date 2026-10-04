package com.projectmanagement.projecttaskmanagementsystem.repository;

import com.projectmanagement.projecttaskmanagementsystem.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}