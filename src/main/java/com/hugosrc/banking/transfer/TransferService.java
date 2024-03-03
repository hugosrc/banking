package com.hugosrc.banking.transfer;

import com.hugosrc.banking.account.Account;
import com.hugosrc.banking.account.AccountRepository;
import com.hugosrc.banking.exception.BusinessLogicException;
import com.hugosrc.banking.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void create(TransferCreateRequest transferCreateRequest) {
        Account fromAccount = this.accountRepository.findByNumberAndDigit(transferCreateRequest.fromNumber(), transferCreateRequest.fromDigit()).orElseThrow(
                () -> new ResourceNotFoundException("from account not found"));

        BigDecimal transferAmount = transferCreateRequest.amount();
        if (fromAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new BusinessLogicException("insufficient balance for the transfer");
        }

        Account toAccount = this.accountRepository.findByNumberAndDigit(transferCreateRequest.toNumber(), transferCreateRequest.toDigit()).orElseThrow(
                () -> new ResourceNotFoundException("to account not found"));

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));

        this.accountRepository.save(fromAccount);
        this.accountRepository.save(toAccount);

        Transfer transfer = new Transfer(transferAmount, fromAccount, toAccount);
        this.transferRepository.save(transfer);
    }
}
