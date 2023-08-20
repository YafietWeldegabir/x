package com.x.authservice.Controller;

import com.x.authservice.Entity.User;
import com.x.authservice.Mapper.Mapper;
import com.x.authservice.Service.AuthService;
import com.x.authservice.dto.UserDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final Mapper mapper;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService, Mapper mapper, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Validation failed");
            response.put("errors", errors);

            return ResponseEntity.badRequest().body(response);
        }

        return authService.saveUser(mapper.convertToEntity(userDto)); // Directly return the result from the service method
    }


    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody @Valid UserDto authRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            // Set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String token = authService.generateToken(authRequest.getEmail());

            // Return authenticated response with token
            Map<String, String> response = new HashMap<>();
            response.put("status", "authenticated");
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid credentials: " + ae.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Authentication error: " + e.getMessage()));
        }
    }


    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        return authService.validateToken(token);

    }


}
