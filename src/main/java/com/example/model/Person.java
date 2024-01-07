package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "Fio must be in format: Surname Name Middle-name")
    @NotEmpty(message = "Fio must not be empty")
    @Size(min = 5, max = 300, message = "Fio size must be from 5 to 300 symbols")
    private String fullName;

    @Column(name = "birth_year")
    @NotNull(message = "Birth year must not be empty")
    @Min(value = 1900, message = "Birth year must be greater than or equal to 1900")
    private Integer birthYear;

    public Person() {
    }

    public Person(Integer id, String fio, int birthYear) {
        this.id = id;
        this.fullName = fio;
        this.birthYear = birthYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
}
