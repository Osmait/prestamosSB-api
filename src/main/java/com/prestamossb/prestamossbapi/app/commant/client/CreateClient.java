package com.prestamossb.prestamossbapi.app.commant.client;

import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateClient {

    private final ClientRepository  clientRepository;


    public void create(ClientRequest client){

        clientRepository.save(client.toClient());
    }
}
