package com.hugosrc.banking.person;

import com.hugosrc.banking.exception.DuplicateResourceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldReturnAllPersons() {
        // Arrange
        List<Person> persons = Arrays.asList(
                new Person(1, "John Doe", "123456789", "12345678902"),
                new Person(2, "Jane Doe", "987654321", "98765432101")
        );

        when(personRepository.findAll()).thenReturn(persons);

        // Act
        List<PersonDTO> result = personService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).name());
        assertEquals("Jane Doe", result.get(1).name());
    }

    @Test
    void shouldCreatePersonSuccessfully() {
        // Arrange
        PersonCreateRequest createRequest = new PersonCreateRequest("John Doe", "12345678902", "123456789");

        when(personRepository.existsByDocument(createRequest.document())).thenReturn(false);

        // Act
        personService.create(createRequest);

        // Assert
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void shouldThrowDuplicateResourceExceptionWhenCreatingExistingPerson() {
        // Arrange
        PersonCreateRequest createRequest = new PersonCreateRequest("John Doe", "12345678902", "123456789");

        when(personRepository.existsByDocument(createRequest.document())).thenReturn(true);

        // Act and Assert
        assertThrows(DuplicateResourceException.class, () -> personService.create(createRequest));
    }
}
