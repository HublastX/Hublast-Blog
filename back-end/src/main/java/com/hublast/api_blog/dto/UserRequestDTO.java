package com.hublast.api_blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(description = "Data required to create a new user")
public class UserRequestDTO {

    @Schema(description = "Username of the user", example = "john_doe", required = true)
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Email address of the user", example = "john@example.com", required = true)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "User password (should be securely hashed before storing)", example = "P@ssw0rd123", required = true)
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}