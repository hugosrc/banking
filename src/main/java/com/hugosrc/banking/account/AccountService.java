package com.hugosrc.banking.account;

import com.hugosrc.banking.exception.BusinessLogicException;
import com.hugosrc.banking.exception.DuplicateResourceException;
import com.hugosrc.banking.exception.ResourceNotFoundException;
import com.hugosrc.banking.person.Person;
import com.hugosrc.banking.person.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PersonRepository personRepository;

    public List<AccountDTO> findAll() {
        return this.accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(
                        account.getId(),
                        account.getNumber(),
                        account.getDigit(),
                        account.getType(),
                        account.getBalance()))
                .toList();
    }

    public AccountDTO findOne(int number, int digit) {
        Account account = this.accountRepository.findByNumberAndDigit(number, digit)
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

        return new AccountDTO(
                account.getId(),
                account.getNumber(),
                account.getDigit(),
                account.getType(),
                account.getBalance());
    }

    public void create(AccountCreateRequest accountCreateRequest) {
        if (this.accountRepository.existsByNumberAndDigit(accountCreateRequest.number(), accountCreateRequest.digit())) {
            throw new DuplicateResourceException("account already exists");
        }

        Person person = this.personRepository.findById(accountCreateRequest.personId()).orElseThrow(
                () -> new ResourceNotFoundException("person not found"));

        boolean hasExistingAccountType = person.getAccounts().stream().anyMatch(
                account -> account.getType() == accountCreateRequest.type());

        if (person.getAccounts().size() >= 2 || hasExistingAccountType) {
            throw new BusinessLogicException("person cannot have more than two accounts or accounts of the same type.");
        }

        Account account = new Account(
                accountCreateRequest.number(),
                accountCreateRequest.digit(),
                accountCreateRequest.type(),
                accountCreateRequest.balance(),
                person);

        this.accountRepository.save(account);
    }

    public void deposit(AccountDepositRequest accountDepositRequest) {
        Account account = accountRepository.findByNumberAndDigit(accountDepositRequest.number(), accountDepositRequest.digit())
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

        account.setBalance(account.getBalance().add(accountDepositRequest.amount()));
        this.accountRepository.save(account);
    }

    public void withdraw(AccountWithdrawRequest accountWithdrawRequest) {
        Account account = this.accountRepository.findByNumberAndDigit(accountWithdrawRequest.number(), accountWithdrawRequest.digit())
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

        if (account.getBalance().compareTo(accountWithdrawRequest.amount()) < 0) {
            throw new BusinessLogicException("insufficient balance for the withdraw");
        }

        account.setBalance(account.getBalance().subtract(accountWithdrawRequest.amount()));
        this.accountRepository.save(account);
    }
}
