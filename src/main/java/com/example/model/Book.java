package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name must not be null")
    @Size(min = 1, max = 200, message = "Name size must be from 1 to 200 symbols")
    private String name;

    @NotEmpty(message = "Author name must not be empty")
    @Size(min = 3, max = 300, message = "Author name must be from 3 to 300 symbols")
    private String author;

    @NotNull(message = "Year must not be null")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    public Book() {
    }

    public Book(Integer id, String name, String author, Integer year, Person owner) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
