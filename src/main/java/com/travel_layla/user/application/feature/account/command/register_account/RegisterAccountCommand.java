package com.travel_layla.user.application.feature.account.command.register_account;

public record RegisterAccountCommand(
        String email,
        String password,
        String firstName,
        String lastName
) {
}
