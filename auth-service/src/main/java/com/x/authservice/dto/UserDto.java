package com.x.authservice.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

@Data
public class UserDto {
    private String name;
    @Email(message = "Please provide a valid email address")
    private String email;
    private String password;
}
