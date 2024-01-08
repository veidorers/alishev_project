CREATE TABLE Person(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name VARCHAR(300) UNIQUE NOT NULL,
    birth_year INT NOT NULL
);

CREATE TABLE Book(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    author VARCHAR(300) NOT NULL,
    year INT NOT NULL,
    person_id INT REFERENCES Person(id) ON DELETE SET NULL,
    time_borrowed TIMESTAMP
);