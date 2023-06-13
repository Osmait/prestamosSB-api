package com.prestamossb.prestamossbapi.app.query.Transaction;

import com.prestamossb.prestamossbapi.domain.transaction.Transaction;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionRequest;
import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionFind {

    private final TransactionRepository transactionRepository;

    public List<TransactionResponse> FindAll(UUID id){
         List<Transaction> transactionList =  transactionRepository
                 .findAllByLoanId(id)
                 .orElseThrow(()-> new RuntimeException("Error Find Transaction"));
         return transactionList.stream().map(transaction -> new TransactionResponse(
                 transaction.getId(),
                 transaction.getTransactionType(),
                 transaction.getAmount(),
                 transaction.getCreateAt(),
                 transaction.getUpdateAt()
                 )).toList();
    }
}
