package com.example.SecurityApp.service;

import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.repository.PeopleRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List showAll(String role) throws UsernameNotFoundException {
        List<Person> people = peopleRepository.findAllByRole(role);
        System.out.println(people);
        if(people.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return people;
    }
    public Person findOne(String username){
        Optional<Person> person = peopleRepository.findByUsername(username);
          return person.orElse(null);
    }
    public Person findById(int id) throws UsernameNotFoundException{
        Optional<Person> person = peopleRepository.findById(id);
        if(person.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return person.orElse(null);
    }
    @Transactional
    public void   delete(int id ){
         peopleRepository.deleteById(id);
    }
}
