package com.capgemini.assignment.transactionservice.service;

import com.capgemini.assignment.transactionservice.entity.Transaction;
import com.capgemini.assignment.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    void testRecordTransaction() {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setAccountId(1L);
        mockTransaction.setAmount(BigDecimal.valueOf(100));

        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);

        Transaction transaction = transactionService.recordTransaction(1L, BigDecimal.valueOf(100));

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        assertEquals(1L, transaction.getAccountId());
        assertEquals(BigDecimal.valueOf(100), transaction.getAmount());
    }

    @Test
    void testGetTransactionsByAccountId() {
        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setAccountId(1L);
        mockTransaction.setAmount(BigDecimal.valueOf(100));

        when(transactionRepository.findByAccountId(1L)).thenReturn(List.of(mockTransaction));

        List<Transaction> transactions = transactionService.getTransactionsByAccountId(1L);

        assertEquals(1, transactions.size());
        assertEquals(1L, transactions.get(0).getAccountId());
    }
}
