package com.prestamossb.prestamossbapi.app.commant.client;

import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateClientTest {

    @InjectMocks
    private CreateClient createClient;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setEmail("john.doe@example.com");
        clientRequest.setName("John");
        clientRequest.setLastName("Doe");
        clientRequest.setAddress("123 Street");
        clientRequest.setPhone("0612345678");

        Client client = clientRequest.toClient();



        createClient.create(clientRequest);
        Mockito.verify(clientRepository).save(client);

    }
}