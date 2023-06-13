package com.prestamossb.prestamossbapi.infraestruture.Dto.transaction;

import com.prestamossb.prestamossbapi.domain.transaction.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(UUID id,
                                  TransactionType transactionType,
                                  Double amount,
                                  LocalDateTime CreateAt,
                                  LocalDateTime UpdateAt ) {
}
