package com.prestamossb.prestamossbapi.infraestruture.Dto.client;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientResponse(UUID id, String name, String lastName, String Email, String phone, String address, LocalDateTime createAt, LocalDateTime updateAt) {
}
