package com.x.authservice.Service;

import com.x.authservice.Entity.User;
import com.x.authservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

   public String saveUser(User credential){
       credential.setPassword(passwordEncoder.encode(credential.getPassword()));
       userRepository.save(credential);
       return "User Added.";
   }

   public void validateToken(String token){
        jwtService.validateToken(token);
   }


}
