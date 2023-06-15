package com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions;

import java.time.LocalDateTime;

public record ApiError(String message, int httpStatus, LocalDateTime localDateTime) {
}
