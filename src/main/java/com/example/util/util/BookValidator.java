package com.example.util.util;

import com.example.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        //if book.getYear() equals to null it'll be validated by annotation above the field
        if(book.getYear() != null && book.getYear() > Year.now().getValue()) {
            errors.rejectValue("year", "", "Year must be less than or equal to " + Year.now().getValue());
        }
    }
}
