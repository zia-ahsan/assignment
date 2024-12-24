package com.capgemini.assignment.accountservice.repository;

import com.capgemini.assignment.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
