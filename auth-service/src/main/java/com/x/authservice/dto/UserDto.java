package com.x.authservice.dto;

import com.x.authservice.Validation.ValidEmail;
import com.x.authservice.Validation.ValidPassword;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UserDto {
    private String name;
    @ValidEmail
    private String email;
    @ValidPassword
    private String password;
}
