package com.example.dao;

import com.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getById(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(fio, birth_year) VALUES(?, ?)",
                person.getFio(), person.getBirthYear());
    }

    public void update(Person person) {
        jdbcTemplate.update("UPDATE Person SET fio = ?, birth_year = ? WHERE id = ?",
                person.getFio(), person.getBirthYear(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }

    public Person getByFio(String fio) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE fio = ?", new BeanPropertyRowMapper<>(Person.class), fio)
                .stream().findAny().orElse(null);
    }
}
