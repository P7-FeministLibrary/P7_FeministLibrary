package com.feministlibrary.model.genre;

import java.util.List;

public interface GenreDAOInterface {

    void insert(Genre genre);
    void update(Genre genre);
    void delete(int idGenre);
    Genre getById(int idGenre);
    List<Genre> getAll();
    List<Genre> searchByGenre(String genre);
    Genre getByName(String name);
}
