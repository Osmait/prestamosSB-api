package com.prestamossb.prestamossbapi.app.query.client;

import com.prestamossb.prestamossbapi.app.Auth.AuthService;
import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientResponse;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientFind {


    private final ClientRepository clientRepository;
    private final AuthService authService;

    public List<ClientResponse> findAll(){
         UUID id  = authService.getIdCurrentLoggedUser().getId();

       List<Client> clients = clientRepository.findAllByUserIdAndDeletedFalse(id).orElseThrow(()-> new NotFoundException("not found by "+id));
        return clients.stream().map(client -> new ClientResponse(
                client.getId(),
                client.getName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress(),
                client.getCreateAt(),
                client.getUpdateAt()
        )).toList();
    }
}
