package com.example.controller;

import com.example.model.Book;
import com.example.service.BooksService;
import com.example.service.PeopleService;
import com.example.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BooksService booksService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookValidator bookValidator, PeopleService peopleService, BooksService booksService) {
        this.bookValidator = bookValidator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping
    public String getAllBooks(Model model,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                              @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        model.addAttribute("books", booksService.findAll(page, booksPerPage, sortByYear));

        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") int id, Model model) {
        var book = booksService.findById(id);
        model.addAttribute("book", book);
        if(book.getPersonId() != null) {
            model.addAttribute("person", peopleService.findById(book.getPersonId()));
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/oneBook";
    }

    @GetMapping("/new")
    public String createPage(@ModelAttribute("book") Book book) {
        return "books/createBookPage";
    }

    @PostMapping
    public String add(@ModelAttribute("book") @Valid Book book,
                      BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) {
            return "books/createBookPage";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "books/editBookPage";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) {
            return "books/editBookPage";
        }
        booksService.update(book);
        return "redirect:/books/" + book.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }


    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable("id") int id) {
        booksService.returnBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/reserve")
    public String reserveBook(@ModelAttribute("book") Book book) {
        booksService.reserve(book);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/searchPage";
    }

    @PostMapping("/search")
    public String search(@RequestParam("bookNameStartingWith") String nameStartingWith,
                         Model model) {
        model.addAttribute("books", booksService.findByNameStartingWith(nameStartingWith));
        return "/books/searchPage";
    }
}
