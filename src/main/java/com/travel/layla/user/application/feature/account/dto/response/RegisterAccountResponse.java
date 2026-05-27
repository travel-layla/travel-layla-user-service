package com.travel.layla.user.application.feature.account.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterAccountResponse(
        UUID id,
        String email,
        String firstName,
        String lastName,
        LocalDateTime createdAt
) {
}
