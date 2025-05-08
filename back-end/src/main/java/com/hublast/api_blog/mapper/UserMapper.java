package com.hublast.api_blog.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.hublast.api_blog.dto.UserDTO;
import com.hublast.api_blog.model.User;

@Component
@ComponentScan
public class UserMapper implements EntityMapper<UserDTO, User> {

    private static ModelMapper mapper;

    @Autowired
    public void setModelMapper(ModelMapper mapper) {
        UserMapper.mapper = mapper;
    }

    @Override
    public User toEntity(UserDTO dto) {
        return mapper.map(dto, User.class);
    }

    @Override
    public UserDTO toDto(User entity) {
        return mapper.map(entity, UserDTO.class);
    }

    @Override
    public List<User> toEntity(List<UserDTO> dtoList) {
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> toDto(List<User> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
