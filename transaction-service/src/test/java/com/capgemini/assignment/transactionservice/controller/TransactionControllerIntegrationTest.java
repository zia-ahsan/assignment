package com.capgemini.assignment.transactionservice.controller;

import com.capgemini.assignment.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testRecordTransaction() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/transactions/record?accountId=1&amount=100", null, String.class);

        assertEquals(201, response.getStatusCode().value());
        assertTrue(transactionRepository.findAll().stream().anyMatch(transaction -> transaction.getAmount().compareTo(BigDecimal.valueOf(100)) == 0));
    }
}
