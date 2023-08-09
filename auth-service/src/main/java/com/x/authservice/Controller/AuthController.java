package com.x.authservice.Controller;

import com.x.authservice.Entity.User;
import com.x.authservice.Service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String addNewUser(@RequestBody User user){
        return authService.saveUser(user);
    }

    @GetMapping("/token")
    public String getToken(@RequestBody User user){
        return authService.generateToken(user.getName());
    }

    @GetMapping("/validate")
    public String validate(@RequestParam("tken") String token){
        authService.validateToken(token);
        return "token is valid.";
    }

}
