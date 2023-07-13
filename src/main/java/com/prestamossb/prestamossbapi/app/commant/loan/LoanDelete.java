package com.prestamossb.prestamossbapi.app.commant.loan;

import com.prestamossb.prestamossbapi.domain.loan.Loan;
import com.prestamossb.prestamossbapi.domain.loan.LoanRepository;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanDelete {
        private final  LoanRepository loanRepository;

        public void  delete(UUID id){
            Loan loan = loanRepository.findById(id).orElseThrow(()->new NotFoundException("Error Loan not exist"));
            loan.setDeleted(true);
            loanRepository.save(loan);

        }
}
