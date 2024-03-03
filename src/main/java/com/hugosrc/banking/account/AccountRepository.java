package com.hugosrc.banking.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByNumberAndDigit(int number, int digit);
    Optional<Account> findByNumberAndDigit(int number, int digit);
}
