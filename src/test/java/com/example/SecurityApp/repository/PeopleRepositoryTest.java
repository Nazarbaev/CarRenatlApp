package com.example.SecurityApp.repository;

import com.example.SecurityApp.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PeopleRepositoryTest {

    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    void findByUsernameTest() {
        Person person = new Person();
        person.setUsername("testuser");
        person.setPassword("testpassword");
        person.setRole("USER");
        peopleRepository.save(person);

        Optional<Person> foundPerson = peopleRepository.findByUsername("testuser");

        assertThat(foundPerson).isNotEmpty();
        assertThat(foundPerson.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    void findByIdTest() {
        Person person = new Person();
        person.setUsername("testuser");
        person.setPassword("testpassword");
        person.setRole("USER");
        Person savedPerson = peopleRepository.save(person);

        Optional<Person> foundPerson = peopleRepository.findById(savedPerson.getId());

        assertThat(foundPerson).isNotEmpty();
        assertThat(foundPerson.get().getId()).isEqualTo(savedPerson.getId());
    }

    @Test
    void findAllByRoleTest() {
        Person person1 = new Person();
        person1.setUsername("testuser1");
        person1.setPassword("testpassword1");
        person1.setRole("USER");
        peopleRepository.save(person1);

        Person person2 = new Person();
        person2.setUsername("testuser2");
        person2.setPassword("testpassword2");
        person2.setRole("ADMIN");
        peopleRepository.save(person2);

        List<Person> users = peopleRepository.findAllByRole("USER");
        List<Person> admins = peopleRepository.findAllByRole("ADMIN");

        assertThat(users.size()).isEqualTo(1);
        assertThat(admins.size()).isEqualTo(1);
        assertThat(users.get(0).getUsername()).isEqualTo("testuser1");
        assertThat(admins.get(0).getUsername()).isEqualTo("testuser2");
    }
}
