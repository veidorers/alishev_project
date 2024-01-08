package com.example.service;

import com.example.model.Book;
import com.example.model.Person;
import com.example.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository bookRepository;

    @Autowired
    public BooksService(BooksRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAll(Integer page, Integer booksPerPage, boolean sortByYear) {
        if(page != null && booksPerPage != null && sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        if(page != null && booksPerPage != null && !sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
        if((page == null || booksPerPage == null) && sortByYear) {
            return bookRepository.findByOrderByYear();
        }
        return bookRepository.findAll();
    }

    public List<Book> findByNameStartingWith(String nameStartingWith) {
        return bookRepository.findByNameStartingWith(nameStartingWith);
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Book book) {
        var bookFromDB = bookRepository.findById(book.getId());
        bookFromDB.ifPresent(bookFromDB1 -> book.setOwner(bookFromDB1.getOwner()));

        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void returnBook(int id) {
        var bookFromDb = bookRepository.findById(id);
        bookFromDb.ifPresent(book1 -> {
                book1.setOwner(null);
                bookRepository.save(book1);
            }
        );
    }

    @Transactional
    public void reserve(Book book) {
        var bookFromDb = bookRepository.findById(book.getId());
        bookFromDb.ifPresent(book1 -> {
            book1.setTimeBorrowed(LocalDateTime.now());
            book1.setOwner(book.getOwner());
            bookRepository.save(book1);
        });
    }

    public List<Book> findByOwner(Person owner) {
        return bookRepository.findByOwner(owner);
    }
}
