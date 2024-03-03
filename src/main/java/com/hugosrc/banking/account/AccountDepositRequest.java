package com.hugosrc.banking.account;

import java.math.BigDecimal;

public record AccountDepositRequest (
        int number,
        int digit,
        BigDecimal amount
) {}
