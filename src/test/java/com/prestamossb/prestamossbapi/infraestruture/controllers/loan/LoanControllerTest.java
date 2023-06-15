package com.prestamossb.prestamossbapi.infraestruture.controllers.loan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.prestamossb.prestamossbapi.domain.client.Client;
import com.prestamossb.prestamossbapi.domain.client.ClientRepository;
import com.prestamossb.prestamossbapi.domain.loan.Frequency;
import com.prestamossb.prestamossbapi.domain.loan.Loan;
import com.prestamossb.prestamossbapi.domain.loan.LoanRepository;
import com.prestamossb.prestamossbapi.domain.transaction.Transaction;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionRepository;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionType;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
class LoanControllerTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:9.6.12");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
        dymDynamicPropertyRegistry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        dymDynamicPropertyRegistry.add("spring.datasource.username",postgresContainer::getUsername);
        dymDynamicPropertyRegistry.add("spring.datasource.password",postgresContainer::getPassword);
    }



    public static Faker faker;

    @BeforeEach
    public void  setUp(){
         faker = new Faker();

        Client client = new Client();

        client.setName(faker.name().firstName());
        client.setLastName(faker.name().lastName());
        client.setPhone(faker.phoneNumber().cellPhone());
        client.setAddress(faker.address().fullAddress());
        client.setEmail(faker.internet().emailAddress());

        clientRepository.save(client);
    }



    @Test
    void create() throws Exception {

      UUID idClient =  clientRepository.findAll().get(0).getId();

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setAmount(Double.valueOf(faker.commerce().price()));
        loanRequest.setInterest(Double.valueOf(faker.commerce().price(1,10)));
        loanRequest.setFrequency(Frequency.BIWEEKLY);
        loanRequest.setPaymentDate("2023-06-10T18:38:03.448566");
        loanRequest.setAmountOfPayments(6);
        loanRequest.setClientId(idClient);

        String loanRequestString = objectMapper.writeValueAsString(loanRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loanRequestString))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertEquals(1,loanRepository.findAll().size());
    }

    @Test
    void findAll() throws Exception {

        List<Loan> loanList = new ArrayList<>();

       Client clientDb = clientRepository.findAll().get(0);

        for(int i  = 0 ; i < 10 ; i++){
            Loan loanRequest = new Loan();
            loanRequest.setAmount(Double.valueOf(faker.commerce().price()));
            loanRequest.setInterest(Double.valueOf(faker.commerce().price(1,10)));
            loanRequest.setFrequency(Frequency.BIWEEKLY);
            loanRequest.setPaymentDate(LocalDateTime.parse("2023-06-10T18:38:03.448566"));
            loanRequest.setAmountOfPayments(6);
            loanRequest.setClient(clientDb);
            loanList.add(loanRequest);
        }

        loanRepository.saveAll(loanList);

        mockMvc.perform(MockMvcRequestBuilders.get("/loan/ "+clientDb.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(10)));
    }

    @Test
    public void  findBalance() throws Exception {
        Client clientDb = clientRepository.findAll().get(0);
        Loan loanRequest = new Loan();
        loanRequest.setAmount(Double.valueOf(faker.commerce().price()));
        loanRequest.setInterest(Double.valueOf(faker.commerce().price(1,10)));
        loanRequest.setFrequency(Frequency.BIWEEKLY);
        loanRequest.setPaymentDate(LocalDateTime.parse("2023-06-10T18:38:03.448566"));
        loanRequest.setAmountOfPayments(6);
        loanRequest.setClient(clientDb);
        loanRepository.save(loanRequest);

        Loan loanDb =  loanRepository.findAllByClientId(clientDb.getId()).orElseThrow().get(0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.pay);
        transaction.setAmount(1000d);
        transaction.setLoan(loanDb);

        transactionRepository.save(transaction);

        mockMvc.perform(MockMvcRequestBuilders.get("/loan/balance/ "+loanDb.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    public  void  findAllByDate ()throws Exception{
        Client clientDb = clientRepository.findAll().get(0);
        Loan loanRequest = new Loan();
        loanRequest.setAmount(Double.valueOf(faker.commerce().price()));
        loanRequest.setInterest(Double.valueOf(faker.commerce().price(1,10)));
        loanRequest.setFrequency(Frequency.BIWEEKLY);
        loanRequest.setPaymentDate(LocalDateTime.parse("2023-06-01T18:38:03.448566"));
        loanRequest.setSecondPaymentDate(LocalDateTime.parse("2023-06-15T18:38:03.448566"));
        loanRequest.setAmountOfPayments(6);
        loanRequest.setClient(clientDb);
        loanRepository.save(loanRequest);

        Loan loanDb =  loanRepository.findAllByClientId(clientDb.getId()).orElseThrow().get(0);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.pay);
        transaction.setAmount(1000d);
        transaction.setLoan(loanDb);

        transactionRepository.save(transaction);

        mockMvc.perform(MockMvcRequestBuilders.get("/loan/payment-day/"+loanDb.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}