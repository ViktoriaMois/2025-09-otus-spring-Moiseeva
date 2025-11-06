package com.example.service;

import com.example.dao.author.AuthorDao;
import com.example.dao.book.BookDao;
import com.example.dao.genre.GenreDao;
import com.example.domain.Author;
import com.example.domain.Book;
import com.example.domain.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    @Override
    public void create(Scanner sc) {
        String authorName;
        String genreName;
        System.out.println("enter book name");
        String bookName = sc.nextLine();
        System.out.println("do u want to add a new author?");
        if (sc.nextLine().equals("y")) {
            System.out.println("enter author name");
            authorName = sc.nextLine();
            authorDao.insert(new Author(null, authorName));
        } else {
            System.out.println("choose author");
            authorDao.getAll().forEach(System.out::println);
            authorName = sc.nextLine();
        }
        System.out.println("do u want to add a new genre?");
        if (sc.nextLine().equals("y")) {
            System.out.println("enter genre name");
            genreName = sc.nextLine();
            genreDao.insert(new Genre(null, genreName));
        } else {
            System.out.println("choose genre");
            genreDao.getAll().forEach(System.out::println);
            genreName = sc.nextLine();
        }
        bookDao.insert(new Book(null, bookName, authorDao.getByName(authorName).getId(), genreDao.getByName(genreName).getId()));
    }

    @Override
    public void read(Scanner sc) {
        System.out.println("which book do u want to read?");
        bookDao.getAll().forEach(System.out::println);
        long id = sc.nextLong();
        bookDao.getById(id);
    }

    @Override
    public void update(Scanner sc) {
        System.out.println("which book do u want to edit?");
        bookDao.getAll().forEach(System.out::println);
        long id = sc.nextLong();
        sc.nextLine();
        Book book = bookDao.getById(id);
        System.out.println("what do u want to change?");
        System.out.println("name");
        System.out.println("author");
        System.out.println("genre");
        String choice = sc.nextLine();
        switch (choice) {
            case "name":
                System.out.println("enter new name");
                String name = sc.nextLine();
                bookDao.deleteById(id);
                bookDao.insert(new Book(id, name, book.getAuthor(), book.getGenre()));
                break;
            case "author":
                System.out.println("enter new author");
                authorDao.getAll().forEach(System.out::println);
                long author = sc.nextLong();
                bookDao.deleteById(id);
                bookDao.insert(new Book(id, book.getName(), author, book.getGenre()));
                break;
            case "genre":
                System.out.println("enter new genre");
                genreDao.getAll().forEach(System.out::println);
                long genre = sc.nextLong();
                bookDao.deleteById(id);
                bookDao.insert(new Book(id, book.getName(), book.getAuthor(), genre));
                break;
        }
    }

    @Override
    public void delete(Scanner sc) {
        System.out.println("which book do u want to delete?");
        bookDao.getAll().forEach(System.out::println);
        long id = sc.nextLong();
        bookDao.deleteById(id);
    }
}
