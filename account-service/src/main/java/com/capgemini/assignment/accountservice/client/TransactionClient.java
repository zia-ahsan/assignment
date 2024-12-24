package com.capgemini.assignment.accountservice.client;

import com.capgemini.assignment.accountservice.entity.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class TransactionClient {

    private final RestTemplate restTemplate;

    @Value("${transaction.service.url}")
    private String transactionServiceUrl;

    public TransactionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void recordTransaction(Long accountId, BigDecimal amount) {
        String url = transactionServiceUrl + "/transactions/record?accountId=" + accountId + "&amount=" + amount;
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to record transaction");
        }
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        String url = transactionServiceUrl + "/transactions/account/" + accountId;
        ResponseEntity<Transaction[]> response = restTemplate.getForEntity(url, Transaction[].class);

        return Arrays.asList(response.getBody());
    }
}
