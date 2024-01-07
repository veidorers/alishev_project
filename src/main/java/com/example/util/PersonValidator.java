package com.example.util;

import com.example.dao.PersonDao;
import com.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class PersonValidator implements Validator {
    private final PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
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

        var mayBePerson = personDao.getByFio(person.getFullName());
        if(mayBePerson != null && !mayBePerson.getId().equals(person.getId())) {
            errors.rejectValue("fio", "", "Fio must be unique!");
        }
    }
}
