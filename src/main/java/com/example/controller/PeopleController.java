package com.example.controller;

import com.example.model.Person;
import com.example.service.BooksService;
import com.example.service.PeopleService;
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
    private final PeopleService peopleService;
    private final BooksService booksService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService, BooksService booksService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/allPeople";
    }

    @GetMapping("/{id}")
    public String getPersonById(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findById(id);

        model.addAttribute("person", person);
        model.addAttribute("books", booksService.findByOwner(person));
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

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/editPersonPage";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) {
            return "people/editPersonPage";
        }
        peopleService.update(person);
        return "redirect:/people/" + person.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
