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
        genreDaoJdbc.insert(new Genre(1L, "Genre"));
        assertEquals(1, genreDaoJdbc.count());
        genreDaoJdbc.insert(new Genre(2L, "Genre2"));
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
        genreDaoJdbc.insert(new Genre(1L, "Genre"));
        Genre genreExp = genreDaoJdbc.getById(1L);
        assertEquals(1L, genreExp.getId());
    }

    @Test
    void getByName() {
        genreDaoJdbc.insert(new Genre(1L, "Genre1"));
        Genre genre = genreDaoJdbc.getByName("Genre1");
        assertNotNull(genre);
        assertEquals("Genre1", genre.getName());
    }

    @Test
    void insert() {
        Genre genre = new Genre(4L, "Genre4");
        genreDaoJdbc.insert(genre);
        assertEquals(1, genreDaoJdbc.count());
    }

    @Test
    void deleteById() {
        genreDaoJdbc.insert(new Genre(4L, "Genre4"));
        genreDaoJdbc.deleteById(4L);
        assertEquals(0, genreDaoJdbc.count());
    }
}