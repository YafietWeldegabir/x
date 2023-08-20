package com.x.authservice.Service;

import com.x.authservice.Entity.User;
import com.x.authservice.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }


        @Transactional
    public ResponseEntity<?> saveUser(User user) {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                logger.info("AuthService.saveUser: Account already exists with email: " + user.getEmail());
                return ResponseEntity.badRequest().body("An account with the same email address already exists.");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            logger.info("AuthService.saveUser: Saving user: " + user);

            userRepository.save(user);
            logger.info("AuthService.saveUser: User saved: " + user);

            return ResponseEntity.ok("User added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding user: " + e.getMessage());
        }
    }

    public ResponseEntity<String> validateToken(String token) {
        try {
            jwtService.validateToken(token);
            return ResponseEntity.ok("Token is valid.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token: " + e.getMessage());
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
