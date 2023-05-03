package com.example.SecurityApp.util;

import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.service.PersonDetailsService;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;
    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
    Person person = (Person)  target;
    try{
        personDetailsService.loadUserByUsername(person.getUsername());
    }catch (UsernameNotFoundException ignored){
        return;

    }
      errors.rejectValue("username","","Person with this username already exist");
    }
}
