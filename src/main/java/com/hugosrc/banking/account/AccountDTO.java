package com.hugosrc.banking.account;

import java.math.BigDecimal;

public record AccountDTO(
        Integer id,
        int number,
        int digit,
        AccountType type,
        BigDecimal balance
) {}
