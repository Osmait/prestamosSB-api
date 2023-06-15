package com.prestamossb.prestamossbapi.infraestruture.controllers.client;


import com.github.javafaker.Faker;
import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientRequest;


import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
class ClientControllerTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:9.6.12");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;



    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
        dymDynamicPropertyRegistry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        dymDynamicPropertyRegistry.add("spring.datasource.username",postgresContainer::getUsername);
        dymDynamicPropertyRegistry.add("spring.datasource.password",postgresContainer::getPassword);


    }

@Test
void findAll() throws Exception {
    Faker faker = new Faker();
    List<Client> clients = new ArrayList<>();

//        Spawn Client instances with fake data and add them to the list
    for (int i = 0; i < 10; i++) {
        Client client = new Client();
        client.setName(faker.name().firstName());
        client.setLastName(faker.name().lastName());
        client.setPhone(faker.phoneNumber().cellPhone());
        client.setAddress(faker.address().fullAddress());
        client.setEmail(faker.internet().emailAddress());
        clients.add(client);
    }


    clientRepository.saveAll(clients);

    mockMvc.perform(MockMvcRequestBuilders.get("/client"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$",hasSize(10) ));

}



    @Test
    void create() throws Exception {

        Faker faker = new Faker();
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setName(faker.name().firstName());
        clientRequest.setLastName(faker.name().lastName());
        clientRequest.setPhone(faker.phoneNumber().phoneNumber());
        clientRequest.setAddress(faker.address().fullAddress());
        clientRequest.setEmail(faker.internet().emailAddress());

        String clientRequesString = objectMapper.writeValueAsString(clientRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientRequesString))
                .andExpect(status().isCreated())

        ;


        assertEquals(1, clientRepository.findAll().size());
    }


}