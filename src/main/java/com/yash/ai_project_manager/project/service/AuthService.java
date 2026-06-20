package com.yash.ai_project_manager.project.service;

import com.yash.ai_project_manager.project.dto.AuthResponseDTO;
import com.yash.ai_project_manager.project.dto.LoginRequestDTO;
import com.yash.ai_project_manager.project.dto.RegisterRequestDTO;
import com.yash.ai_project_manager.project.entity.User;
import com.yash.ai_project_manager.project.repository.UserRepository;
import com.yash.ai_project_manager.project.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.yash.ai_project_manager.project.dto.MessageResponseDTO;
import com.yash.ai_project_manager.project.entity.EmailVerificationToken;
import com.yash.ai_project_manager.project.repository.EmailVerificationTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailVerificationTokenRepository
            emailVerificationTokenRepository;

    private final EmailService emailService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            EmailVerificationTokenRepository
                    emailVerificationTokenRepository,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailVerificationTokenRepository =
                emailVerificationTokenRepository;
        this.emailService = emailService;
    }

    public MessageResponseDTO register(
            RegisterRequestDTO request
    ) {

        if (userRepository
                .findByEmail(request.email())
                .isPresent()) {

            throw new RuntimeException(
                    "Email already registered"
            );
        }

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());

        user.setPassword(
                passwordEncoder.encode(
                        request.password()
                )
        );

        user.setRole(request.role());
        user.setSkillSet(request.skillSet());
        user.setSprintCapacity(request.sprintCapacity());

        user.setVerified(false);

        userRepository.save(user);

        String token =
                UUID.randomUUID().toString();

        EmailVerificationToken verificationToken =
                new EmailVerificationToken();

        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setUsed(false);

        verificationToken.setExpiryTime(
                LocalDateTime.now().plusDays(1)
        );

        emailVerificationTokenRepository
                .save(verificationToken);

        emailService.sendVerificationEmail(
                user.getEmail(),
                token
        );

        return new MessageResponseDTO(
                "Registration successful. Please verify your email."
        );
    }
    public AuthResponseDTO login(
            LoginRequestDTO request
    ) {

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid Email"
                        )
                );

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

        if (!user.isVerified()) {
            throw new RuntimeException(
                    "Please verify your email before logging in."
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail()
                );

        return new AuthResponseDTO(token);
    }

    public MessageResponseDTO verifyEmail(
            String token
    ) {

        EmailVerificationToken verificationToken =
                emailVerificationTokenRepository
                        .findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid verification token"
                                )
                        );

        if (verificationToken.isUsed()) {
            throw new RuntimeException(
                    "Token already used"
            );
        }

        if (verificationToken
                .getExpiryTime()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Token expired"
            );
        }

        User user =
                verificationToken.getUser();

        user.setVerified(true);

        userRepository.save(user);

        verificationToken.setUsed(true);

        emailVerificationTokenRepository
                .save(verificationToken);

        return new MessageResponseDTO(
                "Email verified successfully."
        );
    }
}