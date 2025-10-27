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
        genreDaoJdbc.insert(new Genre(2L, "Genre"));
        authorDaoJdbc = new AuthorDaoJdbc(namedParameterJdbcOperations);
        authorDaoJdbc.insert(new Author(2L, "Author"));
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
        bookDaoJdbc.insert(new Book(1L, "Book", 2L, 2L));
        assertEquals(1, bookDaoJdbc.count());
        bookDaoJdbc.insert(new Book(2L, "Book2", 2L, 2L));
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
        bookDaoJdbc.insert(new Book(1L, "Book", 2L, 2L));
        Book bookExp = bookDaoJdbc.getById(1L);
        assertEquals(1L, bookExp.getId());
    }

    @Test
    void getByName() {
        bookDaoJdbc.insert(new Book(1L, "Book1", 2L, 2L));
        Book book = bookDaoJdbc.getByName("Book1");
        assertNotNull(book);
        assertEquals("Book1", book.getName());
    }

    @Test
    void insert() {
        Book book = new Book(4L, "Book4", 2L, 2L);
        bookDaoJdbc.insert(book);
        assertEquals(1, bookDaoJdbc.count());
    }

    @Test
    void deleteById() {
        bookDaoJdbc.insert(new Book(4L, "Book4", 2L, 2L));
        bookDaoJdbc.deleteById(4L);
        assertEquals(0, bookDaoJdbc.count());
    }
}