package com.hugosrc.banking.transfer;

import java.math.BigDecimal;

public record TransferCreateRequest(
    BigDecimal amount,
    int fromNumber,
    int fromDigit,
    int toNumber,
    int toDigit
) {}
