package ru.pvn.libraryApp.shell;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pvn.libraryApp.dao.AuthorDaoJdbc;
import ru.pvn.libraryApp.dao.BookDaoJdbc;
import ru.pvn.libraryApp.dao.GenreDaoJdbc;
import ru.pvn.libraryApp.models.Author;
import ru.pvn.libraryApp.models.Book;
import ru.pvn.libraryApp.models.Genre;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@ShellComponent
public class ShellCommands {

    private final AuthorDaoJdbc jdbcAuthor;
    private final GenreDaoJdbc jdbcGenreDao;
    private final BookDaoJdbc jdbcBookDao;

    public ShellCommands(AuthorDaoJdbc jdbcAuthor, GenreDaoJdbc jdbcGenreDao, BookDaoJdbc jdbcBookDao) {
        this.jdbcAuthor = jdbcAuthor;
        this.jdbcGenreDao = jdbcGenreDao;
        this.jdbcBookDao = jdbcBookDao;
    }

    @ShellMethod(value = "run H2 console", key = {"console"})
    public String runConsoleH2() throws SQLException {
        Console.main();
        return "Консоль H2 запущена";
    }

    @ShellMethod(value = "add Author to DB", key = {"add-author"})
    public String addAuthor(@ShellOption String fio,
                            @ShellOption String dateOfBirth,
                            @ShellOption String dayOfDeath) throws SQLException, ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Author author = null;
        author = new Author(jdbcAuthor.getMaxId()+1, fio, format.parse(dateOfBirth), format.parse(dayOfDeath));
        jdbcAuthor.create(author);
        return String.format("Автор %s добавлен", author);
    }

    @ShellMethod(value = "get the author from DB on FIO", key = {"get-author-on-fio"})
    public String getAuthorOnFio(@ShellOption String fio) throws SQLException {
        Author author = jdbcAuthor.getByFio(fio);
        return author.toString();
    }


    @ShellMethod(value = "add Genre to DB", key = {"add-genre"})
    public String addAuthor(@ShellOption String name) throws SQLException {
        Genre genre = new Genre(jdbcGenreDao.getMaxId()+1, name);
        jdbcGenreDao.create(genre);
        return String.format("Жанр %s добавлен", name);
    }

    @ShellMethod(value = "delete book from DB", key = {"delete-book"})
    public String deleteBook(@ShellOption long id) throws SQLException {
        jdbcBookDao.deleteById(id);
        return String.format("Книга id=%s удалена", id);
    }

    @ShellMethod(value = "get book from DB", key = {"get-book"})
    public String getBook(@ShellOption long id) throws SQLException {
        Book book = jdbcBookDao.getById(id);
        return book.toString();
    }

    @ShellMethod(value = "get books from DB", key = {"get-books"})
    public String getBooks() throws SQLException {
        List<Book> books = jdbcBookDao.getAll();
        return books.toString();
    }

    @ShellMethod(value = "add book to DB", key = {"add-book"})
    public String addBook(@ShellOption String name,
                          @ShellOption String isbn,
                          @ShellOption String authorId,
                          @ShellOption String genreId) throws SQLException {
        Book book = new Book();
        book.setId(jdbcBookDao.getMaxId()+1);
        book.setName(name);
        book.setIsbn(isbn);
        Author author = jdbcAuthor.getById(Long.parseLong(authorId));
        book.setAuthor(author);
        book.setGenre(jdbcGenreDao.getById(Long.parseLong(genreId)));

        jdbcBookDao.create(book);
        return "Книга " + book.toString() + "добавлена!";
    }

    @ShellMethod(value = "update book in DB", key = {"update-book"})
    public String updateBookById(@ShellOption String id,
                                 @ShellOption String name,
                                 @ShellOption String isbn,
                                 @ShellOption String authorId,
                                 @ShellOption String genreId) throws SQLException {

            Book bookForUpdate = new Book();
            bookForUpdate.setId(Long.parseLong(id));
            bookForUpdate.setName(name);
            bookForUpdate.setIsbn(isbn);
            bookForUpdate.setAuthor(jdbcAuthor.getById(Long.parseLong(authorId)));
            bookForUpdate.setGenre(jdbcGenreDao.getById(Long.parseLong(genreId)));
            jdbcBookDao.update(bookForUpdate);
            return "Книга " + bookForUpdate.toString() + "обновлена!";
        }
    }






