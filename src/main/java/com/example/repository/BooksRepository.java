package com.example.repository;

import com.example.model.Book;
import com.example.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person owner);

    Page<Book> findAll(Pageable pageable);

    List<Book> findByOrderByYear();

    List<Book> findByNameStartingWith(String nameStartingWith);
}
