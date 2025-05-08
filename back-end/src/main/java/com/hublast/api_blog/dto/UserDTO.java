package com.hublast.api_blog.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String username;

    private String email;

    private String password;

}
