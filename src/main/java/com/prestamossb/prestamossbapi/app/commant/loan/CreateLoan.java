package com.prestamossb.prestamossbapi.app.commant.loan;

import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.domain.loan.Loan;
import com.prestamossb.prestamossbapi.domain.loan.LoanRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateLoan {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;

    public void create(LoanRequest loan){
        Client client = clientRepository.findById(loan.getClientId()).orElseThrow(()-> new RuntimeException("Client Not found"));
        Loan loanDb = loan.getLoanFromDto();
        loanDb.setClient(client);


        loanRepository.save(loanDb);
    }
}
