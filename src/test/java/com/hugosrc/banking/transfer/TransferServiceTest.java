package com.hugosrc.banking.transfer;

import com.hugosrc.banking.account.Account;
import com.hugosrc.banking.account.AccountRepository;
import com.hugosrc.banking.account.AccountType;
import com.hugosrc.banking.exception.BusinessLogicException;
import com.hugosrc.banking.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {
    @Mock
    private TransferRepository transferRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    void shouldTransferBetweenAccountsSuccessfully() {
        // Arrange
        Account accountFrom = new Account(123, 1, AccountType.CHECKING, new BigDecimal(100), null);
        Account accountTo = new Account(456, 0, AccountType.CHECKING, new BigDecimal(0), null);

        when(accountRepository.findByNumberAndDigit(accountFrom.getNumber(), accountFrom.getDigit()))
                .thenReturn(Optional.of(accountFrom));
        when(accountRepository.findByNumberAndDigit(accountTo.getNumber(), accountTo.getDigit()))
                .thenReturn(Optional.of(accountTo));

        TransferCreateRequest transferCreateRequest = new TransferCreateRequest(
                new BigDecimal(100),
                accountFrom.getNumber(),
                accountFrom.getDigit(),
                accountTo.getNumber(),
                accountTo.getDigit()
        );

        // Act
        this.transferService.create(transferCreateRequest);

        // Assert
        assertEquals(new BigDecimal(0), accountFrom.getBalance());
        assertEquals(new BigDecimal(100), accountTo.getBalance());
        verify(accountRepository, times(2)).save(any());
        verify(transferRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowErrorWhenFromAccountNotFound() {
        // Arrange
        int invalidFromNumber = 1001500;
        int invalidFromDigit = 1;

        when(accountRepository.findByNumberAndDigit(invalidFromNumber, invalidFromDigit))
                .thenReturn(Optional.empty());

        TransferCreateRequest transferCreateRequest = new TransferCreateRequest(
                new BigDecimal(100), invalidFromNumber, invalidFromDigit, 520, 2);

        // Act
        assertThrows(ResourceNotFoundException.class,
                () -> this.transferService.create(transferCreateRequest));
    }

    @Test
    void shouldThrowErrorWhenInsufficientBalanceForTransfer() {
        // Arrange
        Account accountFrom = new Account(123, 1, AccountType.CHECKING, new BigDecimal(100), null);
        Account accountTo = new Account(456, 0, AccountType.CHECKING, new BigDecimal(0), null);

        when(accountRepository.findByNumberAndDigit(accountFrom.getNumber(), accountFrom.getDigit()))
                .thenReturn(Optional.of(accountFrom));

        TransferCreateRequest transferCreateRequest = new TransferCreateRequest(
                new BigDecimal(10000),
                accountFrom.getNumber(),
                accountFrom.getDigit(),
                accountTo.getNumber(),
                accountTo.getDigit()
        );

        // Act
        assertThrows(BusinessLogicException.class,
                () -> this.transferService.create(transferCreateRequest));
    }

    @Test
    void shouldThrowErrorWhenToAccountNotFound() {
        // Arrange
        int invalidToNumber = 1001500;
        int invalidToDigit = 1;

        Account accountFrom = new Account(123, 1, AccountType.CHECKING, new BigDecimal(100), null);

        when(accountRepository.findByNumberAndDigit(accountFrom.getNumber(), accountFrom.getDigit()))
                .thenReturn(Optional.of(accountFrom));
        when(accountRepository.findByNumberAndDigit(invalidToNumber, invalidToDigit))
                .thenReturn(Optional.empty());

        TransferCreateRequest transferCreateRequest = new TransferCreateRequest(
                new BigDecimal(100),
                accountFrom.getNumber(),
                accountFrom.getDigit(),
                invalidToNumber,
                invalidToDigit);

        // Act
        assertThrows(ResourceNotFoundException.class,
                () -> this.transferService.create(transferCreateRequest));
    }
}