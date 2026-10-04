package com.projectmanagement.projecttaskmanagementsystem.repository;

import com.projectmanagement.projecttaskmanagementsystem.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    long countByStatus(String status);
}