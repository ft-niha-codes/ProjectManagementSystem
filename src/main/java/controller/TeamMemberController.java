package com.projectmanagement.projecttaskmanagementsystem.controller;

import com.projectmanagement.projecttaskmanagementsystem.TeamMember;
import com.projectmanagement.projecttaskmanagementsystem.repository.TeamMemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeamMemberController {

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberController(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    @GetMapping("/team")
    public String team(Model model) {
        model.addAttribute("member", new TeamMember());
        model.addAttribute("members", teamMemberRepository.findAll());
        return "team";
    }

    @PostMapping("/team/save")
    public String saveMember(@ModelAttribute TeamMember member) {
        teamMemberRepository.save(member);
        return "redirect:/team";
    }

    @GetMapping("/team/edit/{id}")
    public String editMember(@PathVariable Long id, Model model) {

        TeamMember member = teamMemberRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid team member ID"));

        model.addAttribute("member", member);

        return "edit-team-member";
    }

    @PostMapping("/team/update")
    public String updateMember(@ModelAttribute TeamMember member) {
        teamMemberRepository.save(member);
        return "redirect:/team";
    }

    @GetMapping("/team/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        teamMemberRepository.deleteById(id);
        return "redirect:/team";
    }
}