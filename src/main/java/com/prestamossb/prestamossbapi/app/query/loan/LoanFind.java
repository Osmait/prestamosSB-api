package com.prestamossb.prestamossbapi.app.query.loan;


import com.prestamossb.prestamossbapi.domain.loan.Frequency;
import com.prestamossb.prestamossbapi.domain.loan.Loan;
import com.prestamossb.prestamossbapi.domain.loan.LoanRepository;
import com.prestamossb.prestamossbapi.domain.transaction.Transaction;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionRepository;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionType;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanFind {

    private final LoanRepository loanRepository;
    private final TransactionRepository transactionRepository;

    public List<LoanResponse> findAll(UUID id){
        List<Loan>  loanList =loanRepository.findAllByClientId(id).orElseThrow(() -> new RuntimeException("Not fond loan"));

        return loanList.stream().map(loan -> new LoanResponse(
                loan.getId(),
                loan.getAmount(),
                loan.getPaymentDate(),
                loan.getSecondPaymentDate(),
                loan.getInterest(),
                loan.getAmountOfPayments(),
                loan.getFrequency(),
                loan.getCreateAt(),
                loan.getUpdateAt()
        )).toList();
    }

    public List<LoanResponse> findAllByDate(UUID id){
        List<Loan>  loanList =loanRepository.findAllByClientId(id)
                .orElseThrow(() -> new RuntimeException("Not fond loan"));

        List<LoanResponse> loanResponseList =  loanList.stream()
                .map(loan -> new LoanResponse(
                loan.getId(),
                loan.getAmount(),
                loan.getPaymentDate(),
                loan.getSecondPaymentDate(),
                loan.getInterest(),
                loan.getAmountOfPayments(),
                loan.getFrequency(),
                loan.getCreateAt(),
                loan.getUpdateAt()
        )).toList();

        List<LoanResponse> filterLoan = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        for (LoanResponse loan : loanResponseList) {
            if (isPaymentDue(loan, currentDate)) {
                filterLoan.add(loan);
            }
        }
        return  filterLoan;
    }

    public LoanResponse findBalance(UUID loanId){
        Loan loan =  loanRepository.findById(loanId)
                .orElseThrow(()-> new RuntimeException("Error Found loan"));

        List<Transaction> transactionList =  transactionRepository.findAllByLoanId(loanId).
                orElseThrow(()-> new RuntimeException("Error Found loan"));

        Double balanceAmount = getBalance(transactionList, loan);

        return new LoanResponse(
                loan.getId(),
                balanceAmount,
                loan.getPaymentDate(),
                loan.getSecondPaymentDate(),
                loan.getInterest(),
                loan.getAmountOfPayments(),
                loan.getFrequency(),
                loan.getCreateAt(),
                loan.getUpdateAt());
    }

    public Double getBalance(List<Transaction> transactionList, Loan loan){
        Double total = 0d;
        for (Transaction transaction : transactionList) {
            if( transaction.getTransactionType() == TransactionType.pay){
                total += transaction.getAmount();
            }else {
                total -= transaction.getAmount();
            }
        }
        return  loan.getAmount() - total;
    }

    public static boolean isPaymentDue(LoanResponse payment, LocalDateTime currentDate) {
        if(payment.frequency() == Frequency.BIWEEKLY){
            return  payment.paymentDate().getDayOfMonth() == currentDate.getDayOfMonth()
                    || payment.secondPaymentDate().getDayOfMonth() == currentDate.getDayOfMonth();
        }else {
            return payment.paymentDate().getDayOfMonth() == currentDate.getDayOfMonth();
        }
    }


}
