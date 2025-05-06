package com.hublast.api_blog.controller;

import com.hublast.api_blog.Repository.UserRepository;
import com.hublast.api_blog.dto.UserRequestDTO;
import com.hublast.api_blog.dto.UserResponseDTO;
import com.hublast.api_blog.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operations related to user management")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Create a new user", responses = {
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword())); // senha criptografada

        User savedUser = userRepository.save(user);

        UserResponseDTO responseDTO = new UserResponseDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a user by ID", responses = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDTO responseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all users", responses = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> responseDTOs = users.stream()
                .map(user -> new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @Operation(summary = "Update a user", responses = {
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userRequestDTO.getUsername());
            user.setEmail(userRequestDTO.getEmail());
            if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword())); // senha criptografada
            }

            User updatedUser = userRepository.save(user);

            UserResponseDTO responseDTO = new UserResponseDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a user", responses = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
