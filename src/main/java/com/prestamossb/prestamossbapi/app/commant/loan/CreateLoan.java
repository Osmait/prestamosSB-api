package com.prestamossb.prestamossbapi.app.commant.loan;

import com.prestamossb.prestamossbapi.app.Auth.AuthService;
import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.domain.loan.Loan;
import com.prestamossb.prestamossbapi.domain.loan.LoanRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanRequest;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateLoan {

    private final LoanRepository loanRepository;
    private final ClientRepository clientRepository;
    private  final AuthService authService;

    public void create(LoanRequest loan){
        UUID userId = authService.getIdCurrentLoggedUser().getId();

        Client client = clientRepository.findById(loan.getClientId())
                .orElseThrow(()-> new NotFoundException("Client Not found"));
        Loan loanDb = loan.getLoanFromDto();
        loanDb.setClient(client);
        loanDb.setUserId(userId);

        loanRepository.save(loanDb);
    }
}
