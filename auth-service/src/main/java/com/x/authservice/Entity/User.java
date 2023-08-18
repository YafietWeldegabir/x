package com.x.authservice.Entity;

import com.x.authservice.Validation.ValidEmail;
import com.x.authservice.Validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ValidEmail
    private String email;
    @ValidPassword
    private String password;

}
