package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.AuthResponseDTO;
import com.yash.ai_project_manager.project.dto.LoginRequestDTO;
import com.yash.ai_project_manager.project.dto.RegisterRequestDTO;
import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.repository.UserRepository;
import com.yash.ai_project_manager.project.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO register(
            RegisterRequestDTO request
    ) {

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());

        user.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );

        user.setRole(request.role());

        userRepository.save(user);

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new AuthResponseDTO(token);
    }
    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException("Invalid Email"));

        boolean matched =
                passwordEncoder.matches(
                        request.password(),
                        user.getPassword()
                );

        if (!matched) {
            throw new RuntimeException(
                    "Invalid Password"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new AuthResponseDTO(token);
    }
}