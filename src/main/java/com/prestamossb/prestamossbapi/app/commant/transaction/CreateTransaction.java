package com.prestamossb.prestamossbapi.app.commant.transaction;

import com.prestamossb.prestamossbapi.domain.loan.Loan;
import com.prestamossb.prestamossbapi.domain.loan.LoanRepository;
import com.prestamossb.prestamossbapi.domain.transaction.Transaction;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateTransaction {

    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;


    public void create(TransactionRequest transactionRequest) {
        Loan loan = loanRepository.findById(transactionRequest.getLoanId()).orElseThrow(() -> new RuntimeException("Loan Don't exist"));
        setPaidState(transactionRequest,loan);

        Transaction transaction = transactionRequest.getTransactionFromDto();
        transaction.setLoan(loan);
        transactionRepository.save(transaction);

    }

    public void setPaidState(TransactionRequest transactionRequest, Loan loan ){
        List<Transaction> transactionList = transactionRepository.findAllByLoanId(loan.getId())
                .orElseThrow(() -> new RuntimeException("Error find transactions by id"));


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
