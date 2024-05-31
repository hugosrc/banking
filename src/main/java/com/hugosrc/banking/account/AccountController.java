package com.hugosrc.banking.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
@Tag(name = "Contas", description = "Operações referentes às contas")
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "Buscar conta por número e dígito")
    private AccountDTO show(
            @RequestParam("number") int number,
            @RequestParam("digit") int digit
    ) {
        return this.accountService.findOne(number, digit);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar uma nova conta")
    public void create(@RequestBody AccountCreateRequest accountCreateRequest) {
        this.accountService.create(accountCreateRequest);
    }

    @PostMapping("/deposit")
    @Operation(summary = "Realizar um depósito na conta")
    public void deposit(@RequestBody AccountDepositRequest accountDepositRequest) {
        this.accountService.deposit(accountDepositRequest);
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Realizar um saque na conta")
    public void withdraw(@RequestBody AccountWithdrawRequest accountWithdrawRequest) {
        this.accountService.withdraw(accountWithdrawRequest);
    }
}
