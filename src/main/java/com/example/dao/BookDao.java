package com.example.dao;

import com.example.mappers.BookRowMapper;
import com.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookRowMapper());
    }

    public Book getById(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new BookRowMapper(), id)
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year, person_id) VALUES (?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear(), book.getPersonId());
    }

    public void update(Book book) {
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year = ?, person_id = ? WHERE id = ?",
                book.getName(), book.getAuthor(), book.getYear(), book.getPersonId(), book.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }

    public List<Book> getBooksByPersonId(int personId) {
        List<Book> list = new ArrayList<>();
        list =  jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new BookRowMapper(), personId);
        return list;
    }

    public void returnBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id = ?", id);
    }

    public void reserve(Book book) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", book.getPersonId(), book.getId());
    }
}
