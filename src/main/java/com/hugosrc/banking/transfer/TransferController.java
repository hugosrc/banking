package com.hugosrc.banking.transfer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transfers")
@Tag(name = "Transferências", description = "Operações referente às tranferências")
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    @Operation(summary = "Criar uma nova transferência")
    public void create(@RequestBody TransferCreateRequest transferCreateRequest) {
        this.transferService.create(transferCreateRequest);
    }
}
