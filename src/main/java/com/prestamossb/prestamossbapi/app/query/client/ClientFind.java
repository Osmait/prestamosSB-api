package com.prestamossb.prestamossbapi.app.query.client;

import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientFind {


    private final ClientRepository clientRepository;

    public List<ClientResponse> findAll(){

        return clientRepository.findAll().stream().map(client -> new ClientResponse(
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
