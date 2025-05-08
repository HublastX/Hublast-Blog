package com.hublast.api_blog.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    @NotNull(message = "Username cannot be null")
    private String username;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @NotNull(message = "Email cannot be null")
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    @NotBlank(message = "Password cannot be blank")
    private String password;

}

