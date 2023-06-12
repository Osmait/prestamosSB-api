package com.prestamossb.prestamossbapi.app.query.loan;


import com.prestamossb.prestamossbapi.domain.loan.Loan;
import com.prestamossb.prestamossbapi.domain.loan.LoanRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanFind {

    private final LoanRepository loanRepository;

    public List<LoanResponse> findAll(UUID id){
        List<Loan>  loanList =loanRepository.findAllByClientId(id).orElseThrow(() -> new RuntimeException("Not fond loan"));

        return loanList.stream().map(loan -> new LoanResponse(
                loan.getId(),
                loan.getAmount(),
                loan.getPaymentDate(),
                loan.getInterest(),
                loan.getAmountOfPayments(),
                loan.getFrequency(),
                loan.getCreateAt(),
                loan.getUpdateAt()
        )).toList();

    }
}
