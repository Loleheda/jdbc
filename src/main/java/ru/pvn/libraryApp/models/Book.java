package ru.pvn.libraryApp.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    private long id;

    private String name;

    private String isbn;

    private Author author;

    private Genre genre;

    public Book(String name, String isbn, Author author, Genre genre) {
        this.name = name;
        this.isbn = isbn;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "\n id=" + id +
                ",\n name='" + name + '\'' +
                ",\n isbn='" + isbn + '\'' +
                ",\n author='" + author + '\'' +
                ",\n genre=" + genre +
                "}\n";
    }
}
