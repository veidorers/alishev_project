package com.example.util;

import com.example.model.Person;
import com.example.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //if person.getBirthYear() equals to null it'll be validated by annotation above the field
        if( person.getBirthYear() != null && person.getBirthYear() > Year.now().getValue()) {
            errors.rejectValue("birthYear", "", "Birth year must be less than or equal to " + Year.now().getValue());
        }

        var mayBePerson = peopleService.findByFullName(person.getFullName());
        if(mayBePerson != null && !mayBePerson.getId().equals(person.getId())) {
            errors.rejectValue("fullName", "", "Full name must be unique!");
        }
    }
}
