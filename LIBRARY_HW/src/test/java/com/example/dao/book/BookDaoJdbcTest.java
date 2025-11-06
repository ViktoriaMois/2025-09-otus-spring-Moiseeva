package com.example.dao.book;

import com.example.dao.author.AuthorDaoJdbc;
import com.example.dao.genre.GenreDaoJdbc;
import com.example.domain.Author;
import com.example.domain.Book;
import com.example.domain.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class BookDaoJdbcTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcOperations;

    private BookDaoJdbc bookDaoJdbc;

    @MockBean
    private GenreDaoJdbc genreDaoJdbc;
    @MockBean
    private AuthorDaoJdbc authorDaoJdbc;

    @BeforeEach
    void setUp() {
        bookDaoJdbc = new BookDaoJdbc(namedParameterJdbcOperations);
        genreDaoJdbc = new GenreDaoJdbc(namedParameterJdbcOperations);
        genreDaoJdbc.insert(new Genre(null, "Genre"));
        authorDaoJdbc = new AuthorDaoJdbc(namedParameterJdbcOperations);
        authorDaoJdbc.insert(new Author(null, "Author"));
    }

    @AfterEach
    void tearDown() {
        bookDaoJdbc.deleteAll();
        genreDaoJdbc.deleteAll();
        authorDaoJdbc.deleteAll();
    }

    @Test
    void count() {
        assertEquals(0, bookDaoJdbc.count());
        bookDaoJdbc.insert(new Book(null, "Book1", authorDaoJdbc.getByName("Author").getId(), genreDaoJdbc.getByName("Genre").getId()));
        assertEquals(1, bookDaoJdbc.count());
        bookDaoJdbc.insert(new Book(null, "Book2", authorDaoJdbc.getByName("Author").getId(), genreDaoJdbc.getByName("Genre").getId()));
        assertEquals(2, bookDaoJdbc.count());
    }

    @Test
    void getAll() {
        List<Book> books = bookDaoJdbc.getAll();
        assertEquals(1, books.size());
        books.forEach(book -> assertNotNull(book.getId()));
    }

    @Test
    void getById() {
        bookDaoJdbc.insert(new Book(null, "Book3", authorDaoJdbc.getByName("Author").getId(), genreDaoJdbc.getByName("Genre").getId()));
        Book bookExp = bookDaoJdbc.getById(bookDaoJdbc.getByName("Book3").getId());
        assertEquals(bookDaoJdbc.getByName("Book3").getId(), bookExp.getId());
    }

    @Test
    void getByName() {
        bookDaoJdbc.insert(new Book(null, "Book4", authorDaoJdbc.getByName("Author").getId(), genreDaoJdbc.getByName("Genre").getId()));
        Book book = bookDaoJdbc.getByName("Book4");
        assertNotNull(book);
        assertEquals("Book4", book.getName());
    }

    @Test
    void insert() {
        Book book = new Book(null, "Book5", authorDaoJdbc.getByName("Author").getId(), genreDaoJdbc.getByName("Genre").getId());
        bookDaoJdbc.insert(book);
        assertEquals(1, bookDaoJdbc.count());
    }

    @Test
    void deleteById() {
        bookDaoJdbc.insert(new Book(null, "Book6", authorDaoJdbc.getByName("Author").getId(), genreDaoJdbc.getByName("Genre").getId()));
        int amount = bookDaoJdbc.count();
        bookDaoJdbc.deleteById(bookDaoJdbc.getByName("Book6").getId());
        assertEquals(amount - 1, bookDaoJdbc.count());
    }
}