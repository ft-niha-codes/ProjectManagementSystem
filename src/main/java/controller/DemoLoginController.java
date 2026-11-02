package com.projectmanagement.projecttaskmanagementsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Map;

@Controller
public class DemoLoginController {

    private static final Map<String, DemoAccount> DEMO_ACCOUNTS = Map.of(
            "admin", new DemoAccount(
                    "DemoAdmin", "Admin@123", "/admin/dashboard"
            ),
            "manager", new DemoAccount(
                    "DemoManager", "Manager@123", "/manager/dashboard"
            ),
            "employee", new DemoAccount(
                    "DemoEmployee", "Employee@123", "/employee/tasks"
            )
    );

    private final AuthenticationManager authenticationManager;

    private final HttpSessionSecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public DemoLoginController(
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/demo-login/{role}")
    public void loginAsDemoUser(
            @PathVariable String role,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        DemoAccount account = DEMO_ACCOUNTS.get(role.toLowerCase());

        if (account == null) {
            response.sendRedirect("/login?demoError");
            return;
        }

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    account.username(),
                                    account.password()
                            )
                    );

            SecurityContext context =
                    SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            securityContextRepository.saveContext(
                    context, request, response
            );

            response.sendRedirect(account.destination());

        } catch (Exception exception) {
            SecurityContextHolder.clearContext();
            response.sendRedirect("/login?demoError");
        }
    }

    private record DemoAccount(
            String username,
            String password,
            String destination) {
    }
}