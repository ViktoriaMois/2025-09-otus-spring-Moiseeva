package com.example.dao.book;

import com.example.domain.Book;

import java.util.List;

public interface BookDao {
    int count();

    void insert(Book Book);

    Book getById(long id);

    Book getByName(String name);

    List<Book> getAll();

    void deleteAll();

    void deleteById(long id);
}
