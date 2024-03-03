package com.hugosrc.banking.account;

import java.math.BigDecimal;

public record AccountCreateRequest(
        int number,
        int digit,
        BigDecimal balance,
        AccountType type,
        int personId
) {
}
