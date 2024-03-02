package ru.pvn.libraryApp.models;


import lombok.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.pvn.libraryApp.dao.AuthorDaoJdbc;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author {

    private long id;

    private String fio;

    private Date dateOfBirth;

    private Date dateOfDeath;

    @Override
    public String toString() {
        return "Author{" +
                " id='" + id + '\'' +
                ", fio='" + fio + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                '}';
    }

}
