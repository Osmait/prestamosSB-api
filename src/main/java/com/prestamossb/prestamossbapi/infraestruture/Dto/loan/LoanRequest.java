package com.prestamossb.prestamossbapi.infraestruture.Dto.loan;

import com.prestamossb.prestamossbapi.domain.loan.Frequency;
import com.prestamossb.prestamossbapi.domain.loan.Loan;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
public class LoanRequest {

    @NotNull(message = "amount is require")
    private Double amount;

    @NotNull(message = "clientId is require")
    private UUID clientId;

    @NotNull
    private String paymentDate;

    private LocalDateTime secondPaymentDate;

    @NotNull
    private Double interest;


    @NotNull
    private Integer amountOfPayments;

    @NotNull
    private Frequency frequency;


    public Loan getLoanFromDto(){
       Loan loan = new Loan();
       loan.setSecondPaymentDate(secondPaymentDate);
       loan.setAmount(amount);
       loan.setPaymentDate(LocalDateTime.parse(paymentDate, DateTimeFormatter.ISO_DATE_TIME));
       loan.setAmountOfPayments(amountOfPayments);
       loan.setInterest(interest);
       loan.setFrequency(frequency);


        return loan;


    }
}
