package com.yash.ai_project_manager.auth.service;

import com.yash.ai_project_manager.auth.dto.UserRequest;
import com.yash.ai_project_manager.auth.entity.User;
import com.yash.ai_project_manager.auth.repository.UserRepository;
import com.yash.ai_project_manager.auth.security.JwtService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequest request) {
        if(userRepository
                .findByEmail(request.email())
                .isPresent()) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());

        return userRepository.save(user);
    }

    public List<User> getAllUsers(String token) {

        if (!jwtService.isTokenValid(token)) {
            throw new RuntimeException("Invalid token");
        }

        String email = jwtService.extractUsername(token);

        System.out.println("Authenticated user: " + email);

        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    public User loadUserByEmail(String email) {
       return userRepository.findByEmail(email).orElseThrow(() ->
               new RuntimeException("User not found"));
    }
}