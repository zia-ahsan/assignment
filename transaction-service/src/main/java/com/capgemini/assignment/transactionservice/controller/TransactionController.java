package com.capgemini.assignment.transactionservice.controller;

import com.capgemini.assignment.transactionservice.entity.Transaction;
import com.capgemini.assignment.transactionservice.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/record")
    public ResponseEntity<String> recordTransaction(
            @RequestParam(name = "accountId") Long accountId,
            @RequestParam(name = "amount") BigDecimal amount) {
        Transaction transaction = transactionService.recordTransaction(accountId, amount);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Transaction recorded successfully with ID: " + transaction.getId());
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable(name = "accountId") Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }
}
