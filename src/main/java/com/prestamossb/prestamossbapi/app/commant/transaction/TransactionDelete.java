package com.prestamossb.prestamossbapi.app.commant.transaction;

import com.prestamossb.prestamossbapi.domain.transaction.Transaction;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionRepository;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionDelete {
    private final TransactionRepository transactionRepository;


    public void delete(UUID id){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new NotFoundException("Error Transaction id Not exist"));
        System.out.println(transaction);
        transaction.setDeleted(true);
        transactionRepository.save(transaction);
    }
}
