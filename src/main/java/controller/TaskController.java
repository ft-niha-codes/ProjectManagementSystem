package com.projectmanagement.projecttaskmanagementsystem.controller;

import com.projectmanagement.projecttaskmanagementsystem.Task;
import com.projectmanagement.projecttaskmanagementsystem.repository.TaskRepository;
import com.projectmanagement.projecttaskmanagementsystem.repository.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskController(TaskRepository taskRepository,
                          ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {

        model.addAttribute("task", new Task());
        model.addAttribute("tasks", taskRepository.findAll());
        model.addAttribute("projects", projectRepository.findAll());

        return "tasks";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID"));

        model.addAttribute("task", task);
        model.addAttribute("projects", projectRepository.findAll());

        return "edit-task";
    }

    @PostMapping("/tasks/update")
    public String updateTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasks";
    }
}