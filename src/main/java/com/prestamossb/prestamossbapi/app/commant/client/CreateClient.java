package com.prestamossb.prestamossbapi.app.commant.client;

import com.prestamossb.prestamossbapi.app.Auth.AuthService;
import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.domain.user.User;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateClient {

    private final ClientRepository  clientRepository;
    private final AuthService authService;

    public void create(ClientRequest client){
        User currentUser = authService.getIdCurrentLoggedUser();
        Client clientDb = client.toClient();
        clientDb.setUserId(currentUser.getId());

        clientRepository.save(clientDb);
    }
}
