package com.projectmanagement.projecttaskmanagementsystem.repository;

import com.projectmanagement.projecttaskmanagementsystem.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    long countByStatus(String status);
}