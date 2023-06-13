package com.prestamossb.prestamossbapi.infraestruture.controllers.transaction;

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
import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionRequest;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
class TransactionControllerTest {
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

    static Faker faker;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
        dymDynamicPropertyRegistry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        dymDynamicPropertyRegistry.add("spring.datasource.username",postgresContainer::getUsername);
        dymDynamicPropertyRegistry.add("spring.datasource.password",postgresContainer::getPassword);
    }

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

        Client clientDB =  clientRepository.findAll().get(0);

        Loan loanRequest = new Loan();
        loanRequest.setAmount(Double.valueOf(faker.commerce().price()));
        loanRequest.setInterest(Double.valueOf(faker.commerce().price(1,10)));
        loanRequest.setFrequency(Frequency.BIWEEKLY);
        loanRequest.setPaymentDate(LocalDateTime.parse("2023-06-10T18:38:03.448566"));
        loanRequest.setAmountOfPayments(6);
        loanRequest.setClient(clientDB);
        loanRepository.save(loanRequest);
    }
    @Test
    void create() throws Exception {
        UUID loanId =  loanRepository.findAll().get(0).getId();

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(Double.valueOf(faker.commerce().price()));
        transactionRequest.setTransactionType(TransactionType.pay);
        transactionRequest.setLoanId(loanId);

        String transactionRequesString = objectMapper.writeValueAsString(transactionRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionRequesString))
                .andExpect(status().isCreated());

        assertEquals(1,transactionRepository.findAll().size());
    }

    @Test
    void findAll() throws Exception {

        List<Transaction> transactionList =new ArrayList<>();

        Loan loanDb = loanRepository.findAll().get(0);

        for (int i = 0; i < 10; i++) {
            Transaction transaction = new Transaction();
            transaction.setLoan(loanDb);
            transaction.setTransactionType(TransactionType.pay);
            transaction.setAmount(Double.valueOf(faker.commerce().price()));
            transactionList.add(transaction);
        }
        transactionRepository.saveAll(transactionList);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/"+loanDb.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(10)));
    }
}