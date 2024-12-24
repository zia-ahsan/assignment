package com.capgemini.assignment.accountservice.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {

    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
