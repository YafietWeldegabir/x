package com.x.authservice.Controller;

import com.x.authservice.Entity.User;
import com.x.authservice.Mapper.Mapper;
import com.x.authservice.Service.AuthService;
import com.x.authservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final Mapper mapper;

    @Autowired
    public AuthController(AuthService authService,Mapper mapper) {
        this.authService = authService;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserDto userDto) {
        try {
            authService.saveUser(mapper.convertToEntity(userDto));
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user.");
        }
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody User user) {
        String token = authService.generateToken(user.getName());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        return authService.validateToken(token);

    }
}
