package com.hugosrc.banking.person;

public record PersonCreateRequest(
        String name,
        String phone,
        String document
) {
}
