
package com.projectmanagement.projecttaskmanagementsystem;

import jakarta.persistence.*;

@Entity
@Table(name = "team_members")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_admin_id")
    private AppUser ownerAdmin;

    public TeamMember() {
    }

    // ID getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Name getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Email getter and setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Role getter and setter
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Owner admin getter and setter
    public AppUser getOwnerAdmin() {
        return ownerAdmin;
    }

    public void setOwnerAdmin(AppUser ownerAdmin) {
        this.ownerAdmin = ownerAdmin;
    }
}
