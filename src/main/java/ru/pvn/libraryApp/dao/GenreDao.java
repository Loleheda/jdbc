package ru.pvn.libraryApp.dao;

import ru.pvn.libraryApp.models.Genre;

public interface GenreDao {
    Long getMaxId();
    Genre getById(long id);
    void create(Genre genre);
}
