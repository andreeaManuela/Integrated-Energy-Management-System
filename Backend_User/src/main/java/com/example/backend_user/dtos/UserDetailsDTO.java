package com.example.backend_user.dtos;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class UserDetailsDTO {

    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String role;

    public UserDetailsDTO() {
    }

    public UserDetailsDTO( String name, String username, String password, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role=role;
    }

    public UserDetailsDTO(UUID id, String name, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role=role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
