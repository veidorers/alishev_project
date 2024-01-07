package com.example.service;

import com.example.repository.BooksRepository;
import com.example.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestService {
    private final BooksRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public TestService(BooksRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public void test() {
        System.out.println("testing via debug...");
    }
}
