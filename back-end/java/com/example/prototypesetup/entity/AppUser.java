package com.example.prototypesetup.entity;


import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_user_id")
    private Long userid;

    @Column(name = "app_user_full_name", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_role_id", nullable = false)
    private Role role;

    @ManyToMany
    @JoinTable(
        name = "user_location",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    @JsonBackReference
    private Set<Location> locations = new HashSet<>();

    // Getters and setters
    public Long getUserId() { return userid; }
    public void setUserId(Long userId) { this.userid = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Set<Location> getLocations() { return locations; }
    public void setLocations(Set<Location> locations) { this.locations = locations; }
}
