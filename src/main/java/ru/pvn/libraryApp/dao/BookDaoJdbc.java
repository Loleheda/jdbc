package ru.pvn.libraryApp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.pvn.libraryApp.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDaoJdbc jdbcAuthor;
    private final GenreDaoJdbc jdbcGenre;

    private final String  selectBooks = "SELECT id,\n" +
            "                       name, \n" +
            "                       isbn, \n" +
            "                       author_id, \n" +
            "                       genre_id \n" +
            "        FROM BOOKS ";

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, AuthorDaoJdbc jdbcAuthor, GenreDaoJdbc genreJdbc) {
        this.jdbc = jdbc;
        this.jdbcAuthor = jdbcAuthor;
        this.jdbcGenre = genreJdbc;
    }

    @Override
    public Long getMaxId() {
        return jdbc.query("SELECT MAX(id) id FROM BOOKS", new BookIdMapper()).get(0);
    }

    @Override
    public Book getById(long id) {
        return jdbc.query( selectBooks +
                        "WHERE id = :id",
                Map.of("id", id),
                new BookExtractor());
    }

    @Override
    public void create(Book book) {
        jdbc.update("insert into books (" +
                        " NAME,\n" +
                        " ISBN,\n" +
                        " AUTHOR_ID, \n" +
                        " GENRE_ID \n" +
                        ")" +
                        "   values ( " +
                        " :name,\n" +
                        " :isbn,\n" +
                        " :authorId, \n" +
                        " :genreId \n" +
                        ")",
                Map.of( "name", book.getName(),
                        "isbn", book.getIsbn(),
                        "authorId", book.getAuthor().getId(),
                        "genreId", book.getGenre().getId()));
    }

    @Override
    public void update(Book book) {
            jdbc.update("update books  SET" +
                            " NAME = :name,\n" +
                            " ISBN = :isbn,\n" +
                            " AUTHOR_ID = :authorId,\n" +
                            " GENRE_ID = :genreId \n" +
                            "WHERE ID = :id",
                    Map.of("id", book.getId(),
                            "name", book.getName(),
                            "isbn", book.getIsbn(),
                            "authorId", book.getAuthor().getId(),
                            "genreId", book.getGenre().getId()));
        }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM books WHERE id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(selectBooks+
                " ORDER BY id", new BooksExtractor());
    }

    private class BookExtractor implements ResultSetExtractor<Book> {
        @Override
        public Book extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Book> books = new BooksExtractor().extractData(resultSet);
            if (books.isEmpty()) return null;
            else return books.iterator().next();
        }
    }

    private class BooksExtractor implements ResultSetExtractor<List<Book>> {
        @Override
        public List<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Book> books = new HashMap<>();
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("isbn"),
                        jdbcAuthor.getById(resultSet.getLong("author_id")),
                        jdbcGenre.getById(resultSet.getLong("genre_id")));
                books.put(resultSet.getLong("id"), book);
            }
            return new ArrayList<>(books.values());
        }
    }

    private static class BookIdMapper implements RowMapper<Long> {
        @Override
        public Long mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getLong("id");
        }
    }
}

