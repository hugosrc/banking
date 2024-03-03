package com.hugosrc.banking.account;

import com.hugosrc.banking.exception.BusinessLogicException;
import com.hugosrc.banking.exception.DuplicateResourceException;
import com.hugosrc.banking.exception.ResourceNotFoundException;
import com.hugosrc.banking.person.Person;
import com.hugosrc.banking.person.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldReturnAllAccounts() {
        // Arrange
        List<Account> accounts = List.of(
                new Account(1, 123, 1, AccountType.CHECKING, new BigDecimal(100), null),
                new Account(2, 456, 0, AccountType.SAVING, new BigDecimal(500), null)
        );

        when(accountRepository.findAll()).thenReturn(accounts);

        // Act
        List<AccountDTO> result = accountService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(accounts.get(0).getBalance(), result.get(0).balance());
        assertEquals(accounts.get(1).getBalance(), result.get(1).balance());
    }

    @Test
    void shouldReturnOneAccount() {
        // Arrange
        int accountNumber = 123;
        int accountDigit = 1;

        Account account = new Account(1, accountNumber, accountDigit, AccountType.CHECKING, new BigDecimal(100), null);

        when(accountRepository.findByNumberAndDigit(accountNumber, accountDigit))
                .thenReturn(Optional.of(account));

        // Act
        AccountDTO result = accountService.findOne(accountNumber, accountDigit);

        // Assert
        assertNotNull(result);
        assertEquals(accountNumber, result.number());
        assertEquals(accountDigit, result.digit());
        assertEquals(new BigDecimal(100), result.balance());
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        // Arrange
        int personId = 1;
        Person person = new Person(personId, "John Doe", "123456789", "123-456-7890", new ArrayList<>());
        AccountCreateRequest createRequest = new AccountCreateRequest(123, 2, new BigDecimal(0), AccountType.CHECKING, personId);

        when(accountRepository.existsByNumberAndDigit(createRequest.number(), createRequest.digit()))
                .thenReturn(false);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        // Act
        accountService.create(createRequest);

        // Assert
        verify(personRepository, times(1)).findById(personId);
        verify(accountRepository, times(1)).existsByNumberAndDigit(createRequest.number(), createRequest.digit());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void shouldThrowDuplicateResourceExceptionWhenCreatingExistingAccount() {
        // Arrange
        AccountCreateRequest createRequest = new AccountCreateRequest(123, 2, new BigDecimal(0), AccountType.CHECKING, 1);

        when(accountRepository.existsByNumberAndDigit(createRequest.number(), createRequest.digit()))
                .thenReturn(true);

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> accountService.create(createRequest));
    }

    @Test
    void shouldThrowBusinessLogicExceptionWhenCreatingAccountWithInvalidPerson() {
        // Arrange
        int invalidPersonId = 999;
        AccountCreateRequest createRequest = new AccountCreateRequest(123, 2, new BigDecimal(0), AccountType.CHECKING, invalidPersonId);

        when(personRepository.findById(invalidPersonId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.create(createRequest));
    }

    @Test
    void shouldThrowBusinessLogicExceptionWhenCreatingDuplicateAccountType() {
        // Arrange
        int personId = 1;
        List<Account> accounts = List.of(
                new Account(541, 1, AccountType.CHECKING, new BigDecimal(100), null)
        );

        Person person = new Person(personId, "John Doe", "123456789", "123-456-7890", accounts);

        AccountCreateRequest createRequest = new AccountCreateRequest(123, 2, new BigDecimal(0), AccountType.CHECKING, personId);

        when(accountRepository.existsByNumberAndDigit(createRequest.number(), createRequest.digit()))
                .thenReturn(false);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        // Act
        assertThrows(BusinessLogicException.class, () -> accountService.create(createRequest));
    }

    @Test
    void shouldThrowBusinessLogicExceptionWhenPersonAlreadyHasTwoAccounts() {
        // Arrange
        int personId = 1;
        List<Account> accounts = List.of(
                new Account(541, 1, AccountType.CHECKING, new BigDecimal(100), null),
                new Account(544, 1, AccountType.SAVING, new BigDecimal(100), null)
        );

        Person person = new Person(personId, "John Doe", "123456789", "123-456-7890", accounts);

        AccountCreateRequest createRequest = new AccountCreateRequest(123, 2, new BigDecimal(0), AccountType.CHECKING, personId);

        when(accountRepository.existsByNumberAndDigit(createRequest.number(), createRequest.digit()))
                .thenReturn(false);

        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        // Act
        assertThrows(BusinessLogicException.class, () -> accountService.create(createRequest));
    }

    @Test
    void shouldDepositAmountSuccessfully() {
        // Arrange
        int accountNumber = 123;
        int accountDigit = 2;
        BigDecimal initialBalance = new BigDecimal(100);

        AccountDepositRequest depositRequest = new AccountDepositRequest(accountNumber, accountDigit, new BigDecimal(50));

        Account existingAccount = new Account(accountNumber, accountDigit, AccountType.CHECKING, initialBalance, null);

        when(accountRepository.findByNumberAndDigit(accountNumber, accountDigit)).thenReturn(Optional.of(existingAccount));

        // Act
        accountService.deposit(depositRequest);

        // Assert
        verify(accountRepository, times(1)).findByNumberAndDigit(accountNumber, accountDigit);
        verify(accountRepository, times(1)).save(existingAccount);
        assert(existingAccount.getBalance().compareTo(initialBalance.add(depositRequest.amount())) == 0);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDepositingToNonexistentAccount() {
        // Arrange
        int accountNumber = 123;
        int accountDigit = 2;

        AccountDepositRequest depositRequest = new AccountDepositRequest(accountNumber, accountDigit, new BigDecimal(50));

        when(accountRepository.findByNumberAndDigit(accountNumber, accountDigit)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.deposit(depositRequest));
        verify(accountRepository, times(1)).findByNumberAndDigit(accountNumber, accountDigit);
    }

    @Test
    void shouldWithdrawAmountSuccessfully() {
        // Arrange
        int accountNumber = 123;
        int accountDigit = 2;
        BigDecimal initialBalance = new BigDecimal(100);

        AccountWithdrawRequest withdrawRequest = new AccountWithdrawRequest(accountNumber, accountDigit, new BigDecimal(50));

        Account existingAccount = new Account(accountNumber, accountDigit, AccountType.CHECKING, initialBalance, null);

        when(accountRepository.findByNumberAndDigit(accountNumber, accountDigit)).thenReturn(Optional.of(existingAccount));

        // Act
        accountService.withdraw(withdrawRequest);

        // Assert
        verify(accountRepository, times(1)).findByNumberAndDigit(accountNumber, accountDigit);
        verify(accountRepository, times(1)).save(existingAccount);
        assert(existingAccount.getBalance().compareTo(initialBalance.subtract(withdrawRequest.amount())) == 0);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenWithdrawingFromNonexistentAccount() {
        // Arrange
        int accountNumber = 123;
        int accountDigit = 2;

        AccountWithdrawRequest withdrawRequest = new AccountWithdrawRequest(accountNumber, accountDigit, new BigDecimal(50));

        when(accountRepository.findByNumberAndDigit(accountNumber, accountDigit)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountService.withdraw(withdrawRequest));
        verify(accountRepository, times(1)).findByNumberAndDigit(accountNumber, accountDigit);
    }

    @Test
    void shouldThrowBusinessLogicExceptionWhenWithdrawingInsufficientBalance() {
        // Arrange
        int accountNumber = 123;
        int accountDigit = 2;
        BigDecimal initialBalance = new BigDecimal(50);

        AccountWithdrawRequest withdrawRequest = new AccountWithdrawRequest(accountNumber, accountDigit, new BigDecimal(100));

        Account existingAccount = new Account(accountNumber, accountDigit, AccountType.CHECKING, initialBalance, null);

        when(accountRepository.findByNumberAndDigit(accountNumber, accountDigit)).thenReturn(Optional.of(existingAccount));

        // Act and Assert
        assertThrows(BusinessLogicException.class, () -> accountService.withdraw(withdrawRequest));
        verify(accountRepository, times(1)).findByNumberAndDigit(accountNumber, accountDigit);
    }
}
