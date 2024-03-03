package com.hugosrc.banking.person;

public record PersonDTO(
        Integer id,
        String name,
        String document,
        String phone
) {
}
