package com.prestamossb.prestamossbapi.infraestruture.Dto.transaction;

import com.prestamossb.prestamossbapi.domain.transaction.Transaction;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionRequest {

    @NotNull(message = "TransactionType is require")
    private TransactionType transactionType;

    @NotNull(message = "amount is require")
    private Double amount;

    @NotNull(message = "loanId is require")
    private UUID loanId;

    public Transaction getTransactionFromDto(){
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);

        return transaction;
    }

}
