package com.hugosrc.banking.person;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/persons")
@Tag(name = "Pessoas", description = "Operações referente às pessoas")
public class PersonController {
    private final PersonService personService;

    @GetMapping
    @Operation(summary = "Buscar por lista de pessoas")
    public List<PersonDTO> index() {
        return this.personService.findAll();
    }

    @PostMapping
    @Operation(summary = "Criar uma nova pessoa")
    public void create(@RequestBody PersonCreateRequest personCreateRequest) {
        this.personService.create(personCreateRequest);
    }
}
