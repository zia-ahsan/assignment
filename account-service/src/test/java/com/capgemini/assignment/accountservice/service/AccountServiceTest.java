package com.capgemini.assignment.accountservice.service;

import com.capgemini.assignment.accountservice.client.TransactionClient;
import com.capgemini.assignment.accountservice.entity.Account;
import com.capgemini.assignment.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private TransactionClient transactionClient;
    private AccountService accountService;

    @BeforeEach
    void setup() {
        accountRepository = Mockito.mock(AccountRepository.class);
        transactionClient = Mockito.mock(TransactionClient.class);
        accountService = new AccountService(accountRepository, transactionClient);
    }

    @Test
    void testCreateAccount_WithInitialCredit() {
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setCustomerId(1L);
        mockAccount.setBalance(BigDecimal.valueOf(100));

        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        Account account = accountService.createAccount(1L, BigDecimal.valueOf(100)).join();

        verify(accountRepository, times(1)).save(any(Account.class));
        verify(transactionClient, times(1)).recordTransaction(1L, BigDecimal.valueOf(100));
        assertEquals(1L, account.getCustomerId());
        assertEquals(BigDecimal.valueOf(100), account.getBalance());
    }

    @Test
    void testGetAccountsWithTransactions() {
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setCustomerId(1L);
        mockAccount.setBalance(BigDecimal.valueOf(100));

        when(accountRepository.findAll()).thenReturn(List.of(mockAccount));
        when(transactionClient.getTransactionsByAccountId(1L)).thenReturn(List.of());

        List<Account> accounts = accountService.getAccountsWithTransactions(1L).join();

        assertEquals(1, accounts.size());
        assertEquals(1L, accounts.get(0).getCustomerId());
        verify(transactionClient, times(1)).getTransactionsByAccountId(1L);
    }
}
