package ru.pvn.libraryApp.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.pvn.libraryApp.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long getMaxId() {
        return jdbc.query("SELECT MAX(id) id FROM AUTHORS", new AuthorIdMapper()).get(0);
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("SELECT * FROM AUTHORS WHERE ID = :id",
                Map.of("id", id),
                new AuthorMapper());
    }

    @Override
    public Author getByFio(String name) {
        return jdbc.queryForObject("SELECT * FROM AUTHORS WHERE FIO = :name",
                Map.of("name", name),
                new AuthorMapper());
    }

    @Override
    public void create(Author author) {
        jdbc.update("insert into authors (" +
                        "    fio,\n" +
                        "    date_of_birch,\n" +
                        "    date_of_death\n" +
                        ")"+
                        "   values ( " +
                        "      :fio,\n" +
                        "      :dateOfBirch,\n" +
                        "      :dateOfDeath\n" +
                        ")",
                Map.of("fio", author.getFio(),
                       "dateOfBirch", author.getDateOfBirth(),
                       "dateOfDeath", author.getDateOfDeath()));
        }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"),
                    resultSet.getString("fio"),
                    resultSet.getDate("date_of_birch"),
                    resultSet.getDate("date_of_death"));
        }
    }

    private static class AuthorIdMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getLong("id");
        }
    }
}


