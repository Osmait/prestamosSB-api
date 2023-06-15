package com.prestamossb.prestamossbapi.app.commant.client;

import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientRequest;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientUpdate {

    private final ClientRepository clientRepository;


    public void update(ClientRequest clientRequest){
        clientRepository.findById(clientRequest.getId())
                .orElseThrow(()-> new NotFoundException("No found client with this id "+ clientRequest.getId()));


        Client client = clientRequest.toClient();
        clientRepository.save(client);
    }
}
