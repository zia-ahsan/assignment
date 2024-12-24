package com.capgemini.assignment.accountservice.controller;

import com.capgemini.assignment.accountservice.client.TransactionClient;
import com.capgemini.assignment.accountservice.entity.Account;
import com.capgemini.assignment.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class AccountControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionClient transactionClient; // Injected mock

    @Test
    void testCreateAccount() {
        // Mock the transaction client behavior
        doNothing().when(transactionClient).recordTransaction(anyLong(), any(BigDecimal.class));

        // Send POST request to create an account
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/accounts/create?customerId=1&initialCredit=100", null, String.class);

        // Verify the response
        assertEquals(201, response.getStatusCode().value());

        // Assert that the account exists with the correct balance
        assertTrue(accountRepository.findAll()
                .stream()
                .anyMatch(account -> account.getBalance().compareTo(BigDecimal.valueOf(100)) == 0));

        verify(transactionClient, times(1)).recordTransaction(anyLong(), any(BigDecimal.class));
    }

    @TestConfiguration
    static class MockConfig {

        @Bean
        public TransactionClient transactionClient() {
            return mock(TransactionClient.class);
        }
    }
}
