package com.travel_layla.user.presentation.controller;

import com.travel_layla.user.application.feature.account.command.register_account.RegisterAccountCommand;
import com.travel_layla.user.application.feature.account.command.register_account.RegisterAccountHandler;
import com.travel_layla.user.application.feature.account.dto.request.RegisterAccountRequest;
import com.travel_layla.user.application.feature.account.dto.response.RegisterAccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class UserController {

    private final RegisterAccountHandler registerAccountHandler;

    public UserController(RegisterAccountHandler registerAccountHandler) {
        this.registerAccountHandler = registerAccountHandler;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterAccountResponse> registerAccount(
            @Valid @RequestBody RegisterAccountRequest request
    ) {
        RegisterAccountCommand command = new RegisterAccountCommand(
                request.email(),
                request.password(),
                request.firstName(),
                request.lastName()
        );

        RegisterAccountResponse response = registerAccountHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
