package com.hugosrc.banking.person;

import com.hugosrc.banking.exception.DuplicateResourceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        return this.personRepository.findAll()
                .stream()
                .map(person -> new PersonDTO(
                        person.getId(),
                        person.getName(),
                        person.getDocument(),
                        person.getPhone()))
                .toList();
    }

    public void create(PersonCreateRequest personCreateRequest) {
        if (personRepository.existsByDocument(personCreateRequest.document())) {
            throw new DuplicateResourceException("person already exists");
        }

        Person person = new Person(
                personCreateRequest.name(),
                personCreateRequest.phone(),
                personCreateRequest.document());

        this.personRepository.save(person);
    }
}
