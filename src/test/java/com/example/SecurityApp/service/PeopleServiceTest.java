package com.example.SecurityApp.service;

import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.repository.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleService peopleService;

    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setId(1);
        person.setUsername("test");
        person.setRole("ROLE_USER");
    }

    @Test
    public void showAllTest() {
        String role = "ROLE_USER";
        List<Person> people = Arrays.asList(person);

        when(peopleRepository.findAllByRole(role)).thenReturn(people);

        List<Person> result = peopleService.showAll(role);

        assertEquals(1, result.size());
        assertEquals(person, result.get(0));
        verify(peopleRepository).findAllByRole(role);
    }

    @Test
    public void showAllTest_emptyResult() {
        String role = "ROLE_USER";

        when(peopleRepository.findAllByRole(role)).thenReturn(List.of());

        assertThrows(UsernameNotFoundException.class, () -> peopleService.showAll(role));
        verify(peopleRepository).findAllByRole(role);
    }

    @Test
    public void findOneTest() {
        String username = "test";

        when(peopleRepository.findByUsername(username)).thenReturn(Optional.of(person));

        Person result = peopleService.findOne(username);

        assertEquals(person, result);
        verify(peopleRepository).findByUsername(username);
    }

    @Test
    public void findOneTest_nullResult() {
        String username = "nonexistent";

        when(peopleRepository.findByUsername(username)).thenReturn(Optional.empty());

        Person result = peopleService.findOne(username);

        assertNull(result);
        verify(peopleRepository).findByUsername(username);
    }

    @Test
    public void findByIdTest() {
        int id = 1;

        when(peopleRepository.findById(id)).thenReturn(Optional.of(person));

        Person result = peopleService.findById(id);

        assertEquals(person, result);
        verify(peopleRepository).findById(id);
    }

    @Test
    public void findByIdTest_notFound() {
        int id = 2;

        when(peopleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> peopleService.findById(id));
        verify(peopleRepository).findById(id);
    }

    @Test
    public void deleteTest() {
        int id = 1;

        peopleService.delete(id);

        verify(peopleRepository).deleteById(id);
    }
}
