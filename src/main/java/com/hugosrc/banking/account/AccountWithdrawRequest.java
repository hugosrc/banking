package com.hugosrc.banking.account;

import java.math.BigDecimal;

public record AccountWithdrawRequest(
        int number,
        int digit,
        BigDecimal amount
) {}
