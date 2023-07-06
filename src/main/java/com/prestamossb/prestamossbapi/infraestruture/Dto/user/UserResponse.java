package com.prestamossb.prestamossbapi.infraestruture.Dto.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String LastName,
        String email,
        String address,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
