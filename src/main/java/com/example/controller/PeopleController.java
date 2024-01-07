package com.example.controller;

import com.example.dao.BookDao;
import com.example.dao.PersonDao;
import com.example.model.Person;
import com.example.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;
    private final BookDao bookDao;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDao personDao, BookDao bookDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.bookDao = bookDao;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", personDao.getAllPeople());
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String getPersonById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getById(id));
        model.addAttribute("books", bookDao.getBooksByPersonId(id));
        return "people/onePerson";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("person") Person person) {
        return "people/createPersonPage";
    }

    @PostMapping
    public String add(@ModelAttribute("person") @Valid Person person,
                      BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            return "people/createPersonPage";
        }

        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getById(id));
        return "people/editPersonPage";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            return "people/editPersonPage";
        }
        personDao.update(person);
        return "redirect:/people/" + person.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDao.delete(id);
        return "redirect:/people";
    }
}
