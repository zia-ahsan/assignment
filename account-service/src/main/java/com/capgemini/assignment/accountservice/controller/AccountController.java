package com.capgemini.assignment.accountservice.controller;

import com.capgemini.assignment.accountservice.entity.Account;
import com.capgemini.assignment.accountservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<String>> createAccount(
            @RequestParam(name = "customerId") Long customerId,
            @RequestParam(name = "initialCredit") java.math.BigDecimal initialCredit) {
        log.info("Received request: customerId={}, initialCredit={}", customerId, initialCredit);
        return accountService.createAccount(customerId, initialCredit)
                .thenApply(account -> ResponseEntity.status(201)
                        .body("Account created successfully with ID: " + account.getId()
                                + " and balance: " + account.getBalance()));
    }

    @GetMapping("/{customerId}")
    public CompletableFuture<ResponseEntity<List<Account>>> getAccountsWithTransactions(
            @PathVariable(name = "customerId") Long customerId) {
        return accountService.getAccountsWithTransactions(customerId)
                .thenApply(accounts -> ResponseEntity.ok(accounts));
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Account>>> getAllAccountsWithTransactions() {
        return accountService.getAllAccountsWithTransactions()
                .thenApply(accounts -> ResponseEntity.ok(accounts));
    }
}
