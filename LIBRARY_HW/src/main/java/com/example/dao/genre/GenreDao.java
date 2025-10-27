package com.example.dao.genre;

import com.example.domain.Genre;

import java.util.List;

public interface GenreDao {

    int count();

    void insert(Genre Genre);

    Genre getById(long id);

    Genre getByName(String name);

    List<Genre> getAll();

    void deleteById(long id);

    void deleteAll();
}
