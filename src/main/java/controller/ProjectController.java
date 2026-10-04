package com.projectmanagement.projecttaskmanagementsystem.controller;

import com.projectmanagement.projecttaskmanagementsystem.Project;
import com.projectmanagement.projecttaskmanagementsystem.repository.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("projects", projectRepository.findAll());
        return "projects";
    }

    @PostMapping("/projects/save")
    public String saveProject(@ModelAttribute Project project) {
        projectRepository.save(project);
        return "redirect:/projects";
    }
    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }
    @GetMapping("/projects/edit/{id}")
    public String editProject(@PathVariable Long id, Model model) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));

        model.addAttribute("project", project);

        return "edit-project";
    }
    @PostMapping("/projects/update")
    public String updateProject(@ModelAttribute Project project) {

        projectRepository.save(project);

        return "redirect:/projects";
    }
}