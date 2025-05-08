package com.hublast.api_blog.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hublast.api_blog.dto.UserDTO;
import com.hublast.api_blog.model.User;
import com.hublast.api_blog.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Operations related to user management")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> list = service.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = service.findById(id);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO objDto) {
        User user = service.insert(objDto);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO objDto) {
        User user = service.update(id, objDto);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(userDTO);
    }
}
