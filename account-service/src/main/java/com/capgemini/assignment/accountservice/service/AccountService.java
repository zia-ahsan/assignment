package com.capgemini.assignment.accountservice.service;

import com.capgemini.assignment.accountservice.client.TransactionClient;
import com.capgemini.assignment.accountservice.entity.Account;
import com.capgemini.assignment.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionClient transactionClient;

    public AccountService(AccountRepository accountRepository, TransactionClient transactionClient) {
        this.accountRepository = accountRepository;
        this.transactionClient = transactionClient;
    }

    @Transactional
    public CompletableFuture<Account> createAccount(Long customerId, BigDecimal initialCredit) {
        // Create Account
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setBalance(initialCredit);

        Account savedAccount = accountRepository.save(account);

        // Record Transaction if initialCredit > 0
        if (initialCredit.compareTo(BigDecimal.ZERO) > 0) {
            transactionClient.recordTransaction(savedAccount.getId(), initialCredit);
        }

        return CompletableFuture.completedFuture(savedAccount);
    }

    public CompletableFuture<List<Account>> getAccountsWithTransactions(Long customerId) {
        List<Account> accounts = accountRepository.findAll()
                .stream()
                .filter(account -> account.getCustomerId().equals(customerId))
                .collect(Collectors.toList());

        return CompletableFuture.supplyAsync(() -> {
            accounts.forEach(account -> account.setTransactions(
                    transactionClient.getTransactionsByAccountId(account.getId())
            ));
            return accounts;
        });
    }

    public CompletableFuture<List<Account>> getAllAccountsWithTransactions() {
        List<Account> accounts = accountRepository.findAll()
                .stream()
                .collect(Collectors.toList());

        return CompletableFuture.supplyAsync(() -> {
            accounts.forEach(account -> account.setTransactions(
                    transactionClient.getTransactionsByAccountId(account.getId())
            ));
            return accounts;
        });
    }
}
