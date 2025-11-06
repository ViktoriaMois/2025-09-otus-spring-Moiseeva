package com.example.dao.author;

import static org.junit.jupiter.api.Assertions.*;

import com.example.domain.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class AuthorDaoJdbcTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcOperations;

    private AuthorDaoJdbc authorDaoJdbc;

    @BeforeEach
    void setUp() {
        authorDaoJdbc = new AuthorDaoJdbc(namedParameterJdbcOperations);
    }

    @AfterEach
    void tearDown() {
        authorDaoJdbc.deleteAll();
    }

    @Test
    void count() {
        assertEquals(0, authorDaoJdbc.count());
        authorDaoJdbc.insert(new Author(null, "Author1"));
        assertEquals(1, authorDaoJdbc.count());
        authorDaoJdbc.insert(new Author(null, "Author2"));
        assertEquals(2, authorDaoJdbc.count());
    }

    @Test
    void getAll() {
        List<Author> authors = authorDaoJdbc.getAll();
        assertEquals(1, authors.size());
        authors.forEach(author -> assertNotNull(author.getId()));
    }

    @Test
    void getById() {
        authorDaoJdbc.insert(new Author(null, "Author3"));
        Author authorExp = authorDaoJdbc.getById(authorDaoJdbc.getByName("Author3").getId());
        assertEquals(authorDaoJdbc.getByName("Author3").getId(), authorExp.getId());
    }

    @Test
    void getByName() {
        authorDaoJdbc.insert(new Author(null, "Author4"));
        Author author = authorDaoJdbc.getByName("Author4");
        assertNotNull(author);
        assertEquals("Author4", author.getFullName());
    }

    @Test
    void insert() {
        Author author = new Author(null, "Author5");
        authorDaoJdbc.insert(author);
        assertEquals(1, authorDaoJdbc.count());
    }

    @Test
    void deleteById() {
        authorDaoJdbc.insert(new Author(null, "Author6"));
        int amount = authorDaoJdbc.count();
        authorDaoJdbc.deleteById(authorDaoJdbc.getByName("Author6").getId());
        assertEquals(amount - 1, authorDaoJdbc.count());
    }
}
