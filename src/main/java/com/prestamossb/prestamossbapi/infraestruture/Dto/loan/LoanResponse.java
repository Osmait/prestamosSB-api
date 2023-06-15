package com.prestamossb.prestamossbapi.infraestruture.Dto.loan;

import com.prestamossb.prestamossbapi.domain.loan.Frequency;

import java.time.LocalDateTime;
import java.util.UUID;

public record LoanResponse(UUID id ,
                           Double amount,
                           LocalDateTime paymentDate,
                           LocalDateTime secondPaymentDate,
                           Double interest,
                           Integer amountOfPayments,
                           Frequency frequency,
                           LocalDateTime CreateAt,
                           LocalDateTime UpdateAt) {
}
