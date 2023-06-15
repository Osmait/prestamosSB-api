package com.prestamossb.prestamossbapi.app.commant.client;


import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientDelete {

    private final ClientRepository clientRepository;


    public void delete(UUID id){
        Client client = clientRepository.findById(id).orElseThrow(()-> new NotFoundException("Not found client by this id "+id));
        client.setDeleted(true);
        clientRepository.save(client);
    }
}
