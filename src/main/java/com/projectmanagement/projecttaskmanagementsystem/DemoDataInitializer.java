package com.projectmanagement.projecttaskmanagementsystem;

import com.projectmanagement.projecttaskmanagementsystem.repository.AppUserRepository;
import com.projectmanagement.projecttaskmanagementsystem.repository.ProjectRepository;
import com.projectmanagement.projecttaskmanagementsystem.repository.TaskRepository;
import com.projectmanagement.projecttaskmanagementsystem.repository.TeamMemberRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@ConditionalOnProperty(
        name = "demo.data-enabled",
        havingValue = "true"
)
public class DemoDataInitializer {

    @Bean
    public CommandLineRunner createDemoAccounts(
            AppUserRepository userRepository,
            TeamMemberRepository teamMemberRepository,
            ProjectRepository projectRepository,
            TaskRepository taskRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // ADMIN
            AppUser admin = userRepository.findByUsername("DemoAdmin")
                    .orElseGet(AppUser::new);

            admin.setFullName("Demo Administrator");
            admin.setUsername("DemoAdmin");
            admin.setEmail("demoadmin@example.com");
            admin.setRole("ADMIN");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setOwnerAdmin(null);

            admin = userRepository.save(admin);

            // MANAGER
            AppUser manager = userRepository.findByUsername("DemoManager")
                    .orElseGet(AppUser::new);

            manager.setFullName("Demo Manager");
            manager.setUsername("DemoManager");
            manager.setEmail("demomanager@example.com");
            manager.setRole("MANAGER");
            manager.setPassword(passwordEncoder.encode("Manager@123"));
            manager.setOwnerAdmin(admin);

            userRepository.save(manager);

            // EMPLOYEE PROFILE
            TeamMember member = teamMemberRepository
                    .findByOwnerAdmin_Id(admin.getId())
                    .stream()
                    .filter(m -> "demoemployee@example.com"
                            .equalsIgnoreCase(m.getEmail()))
                    .findFirst()
                    .orElseGet(TeamMember::new);

            member.setName("Demo Employee");
            member.setEmail("demoemployee@example.com");
            member.setRole("EMPLOYEE");
            member.setOwnerAdmin(admin);

            member = teamMemberRepository.save(member);

            // EMPLOYEE LOGIN
            AppUser employee = userRepository.findByUsername("DemoEmployee")
                    .orElseGet(AppUser::new);

            employee.setFullName("Demo Employee");
            employee.setUsername("DemoEmployee");
            employee.setEmail("demoemployee@example.com");
            employee.setRole("EMPLOYEE");
            employee.setPassword(passwordEncoder.encode("Employee@123"));
            employee.setOwnerAdmin(admin);
            employee.setTeamMember(member);

            userRepository.save(employee);

            // SAMPLE PROJECT
            final Long adminId = admin.getId();

            Project project = projectRepository
                    .findByOwnerAdmin_Id(adminId)
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (project == null) {
                project = new Project();
                project.setName("Faculty Evaluation Demo Project");
                project.setDescription(
                        "Sample project for the faculty demonstration."
                );
                project.setStatus("In Progress");
                project.setDeadline(LocalDate.now().plusDays(14));
                project.setOwnerAdmin(admin);

                project = projectRepository.save(project);
            }

            // SAMPLE TASK
            boolean taskExists = !taskRepository
                    .findByOwnerAdmin_Id(adminId).isEmpty();

            if (!taskExists) {
                Task task = new Task();

                task.setTitle("Complete Demo Project Review");
                task.setDescription(
                        "Review the sample project and update the task status."
                );
                task.setStatus("Pending");
                task.setPriority("Medium");
                task.setTaskRole("EMPLOYEE");
                task.setDeadline(LocalDate.now().plusDays(7));
                task.setProject(project);
                task.setTeamMember(member);
                task.setOwnerAdmin(admin);

                taskRepository.save(task);
            }
        };
    }
}