package com.prestamossb.prestamossbapi.app.commant.transaction;

import com.prestamossb.prestamossbapi.app.Auth.AuthService;
import com.prestamossb.prestamossbapi.domain.loan.Loan;
import com.prestamossb.prestamossbapi.domain.loan.LoanRepository;
import com.prestamossb.prestamossbapi.domain.transaction.Transaction;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionRequest;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateTransaction {

    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;
    private final AuthService authService;


    public void create(TransactionRequest transactionRequest) {
        UUID userId = authService.getIdCurrentLoggedUser().getId();
        Loan loan = loanRepository.findById(transactionRequest.getLoanId()).orElseThrow(() -> new RuntimeException("Loan Don't exist"));
        setPaidState(transactionRequest,loan);

        Transaction transaction = transactionRequest.getTransactionFromDto();
        transaction.setLoan(loan);
        transaction.setUserId(userId);
        transactionRepository.save(transaction);

    }

    public void setPaidState(TransactionRequest transactionRequest, Loan loan ){
        List<Transaction> transactionList = transactionRepository.findAllByLoanIdAndDeletedFalse(loan.getId())
                .orElseThrow(() -> new NotFoundException("Error find transactions by id"));


        Double Total = transactionRequest.getAmount();

        for (Transaction value : transactionList) {
            Total += value.getAmount();
        }
        if (Total >= loan.getAmount()){
            loan.setPaid(true);
            loanRepository.save(loan);
        }

    }


}
