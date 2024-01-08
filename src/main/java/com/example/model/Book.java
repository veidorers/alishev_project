package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @Column(name = "time_borrowed")
    private LocalDateTime timeBorrowed;

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

    public boolean isBorrowExpired() {
        if(LocalDateTime.now().isAfter(timeBorrowed.plus(10, ChronoUnit.DAYS))) {
            return true;
        }
        return false;
    }

    public LocalDateTime getTimeBorrowed() {
        return timeBorrowed;
    }

    public void setTimeBorrowed(LocalDateTime timeBorrowed) {
        this.timeBorrowed = timeBorrowed;
    }
}
