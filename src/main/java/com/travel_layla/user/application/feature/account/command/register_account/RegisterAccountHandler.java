package com.travel_layla.user.application.feature.account.command.register_account;

import com.travel_layla.user.application.feature.account.dto.response.RegisterAccountResponse;
import com.travel_layla.user.domain.User;
import com.travel_layla.user.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterAccountHandler {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterAccountHandler(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterAccountResponse handle(RegisterAccountCommand command) {
        // Check if email already exists
        if (userRepository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create new user with encoded password
        User user = User.builder()
                .email(command.email())
                .password(passwordEncoder.encode(command.password()))
                .firstName(command.firstName())
                .lastName(command.lastName())
                .build();

        // Save user to database
        User savedUser = userRepository.save(user);

        // Return response DTO
        return new RegisterAccountResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getCreatedAt()
        );
    }
}
