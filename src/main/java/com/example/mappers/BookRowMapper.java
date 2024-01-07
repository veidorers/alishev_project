package com.example.mappers;

import com.example.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();

        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setYear(rs.getInt("year"));
        book.setAuthor(rs.getString("author"));

        Integer personId = rs.getObject("person_id", Integer.class);
        if (personId != null) {
            book.setPersonId(personId);
        }

        return book;
    }
}
