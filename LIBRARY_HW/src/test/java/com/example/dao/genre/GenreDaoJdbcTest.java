package com.example.dao.genre;

import com.example.domain.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class GenreDaoJdbcTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcOperations;

    private GenreDaoJdbc genreDaoJdbc;

    @BeforeEach
    void setUp() {
        genreDaoJdbc = new GenreDaoJdbc(namedParameterJdbcOperations);
    }

    @AfterEach
    void tearDown() {
        genreDaoJdbc.deleteAll();
    }

    @Test
    void count() {
        assertEquals(0, genreDaoJdbc.count());
        genreDaoJdbc.insert(new Genre(null, "Genre1"));
        assertEquals(1, genreDaoJdbc.count());
        genreDaoJdbc.insert(new Genre(null, "Genre2"));
        assertEquals(2, genreDaoJdbc.count());
    }

    @Test
    void getAll() {
        List<Genre> genres = genreDaoJdbc.getAll();
        assertEquals(1, genres.size());
        genres.forEach(genre -> assertNotNull(genre.getId()));
    }

    @Test
    void getById() {
        genreDaoJdbc.insert(new Genre(null, "Genre3"));
        Genre genreExp = genreDaoJdbc.getById(genreDaoJdbc.getByName("Genre3").getId());
        assertEquals(genreDaoJdbc.getByName("Genre3").getId(), genreExp.getId());
    }

    @Test
    void getByName() {
        genreDaoJdbc.insert(new Genre(null, "Genre4"));
        Genre genre = genreDaoJdbc.getByName("Genre4");
        assertNotNull(genre);
        assertEquals("Genre4", genre.getName());
    }

    @Test
    void insert() {
        Genre genre = new Genre(null, "Genre5");
        genreDaoJdbc.insert(genre);
        assertEquals(1, genreDaoJdbc.count());
    }

    @Test
    void deleteById() {
        genreDaoJdbc.insert(new Genre(null, "Genre6"));
        int amount = genreDaoJdbc.count();
        genreDaoJdbc.deleteById(genreDaoJdbc.getByName("Genre6").getId());
        assertEquals(amount - 1, genreDaoJdbc.count());
    }
}