package com.example.service;

import com.example.dao.author.AuthorDao;
import com.example.dao.book.BookDao;
import com.example.dao.genre.GenreDao;
import com.example.domain.Author;
import com.example.domain.Book;
import com.example.domain.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
class LibraryServiceImplTest {
    @MockBean
    private BookDao bookDao;
    @MockBean
    private GenreDao genreDao;
    @MockBean
    private AuthorDao authorDao;
    private LibraryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new LibraryServiceImpl(bookDao, genreDao, authorDao);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test Book\ny\nTest Author\ny\nTest Genre"})
    void create_shouldInsertBookWithNewAuthorAndGenre(String userInput) {

        Scanner sc = new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));

        when(authorDao.count()).thenReturn(0);
        when(genreDao.count()).thenReturn(0);
        when(bookDao.count()).thenReturn(0);
        when(authorDao.getByName("Test Author")).thenReturn(new Author(1L, "Test Author"));
        when(genreDao.getByName("Test Genre")).thenReturn(new Genre(1L, "Test Genre"));

        service.create(sc);

        verify(authorDao).insert(new Author(1L, "Test Author"));
        verify(genreDao).insert(new Genre(1L, "Test Genre"));
        verify(bookDao).insert(new Book(1L, "Test Book", 1L, 1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test Book\nn\nTest Author\nn\nTest Genre"})
    void create_shouldInsertBookWithOldAuthorAndGenre(String userInput) {

        Scanner sc = new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));

        when(authorDao.count()).thenReturn(0);
        when(genreDao.count()).thenReturn(0);
        when(bookDao.count()).thenReturn(0);
        when(authorDao.getByName("Test Author")).thenReturn(new Author(1L, "Test Author"));
        when(genreDao.getByName("Test Genre")).thenReturn(new Genre(1L, "Test Genre"));

        service.create(sc);

        verify(bookDao).insert(new Book(1L, "Test Book", 1L, 1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1\nname\nTest"})
    void update_bookName(String userInput) {
        Scanner sc = new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));
        when(bookDao.getById(1L)).thenReturn(new Book(1L, "Name", 1L, 1L));
        service.update(sc);
        verify(bookDao).insert(new Book(1L, "Test", 1L, 1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1\nauthor\n2"})
    void update_bookAuthor(String userInput) {
        Scanner sc = new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));
        when(bookDao.getById(1L)).thenReturn(new Book(1L, "Name", 1L, 1L));
        service.update(sc);
        verify(bookDao).insert(new Book(1L, "Name", 2L, 1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1\ngenre\n2"})
    void update_bookGenre(String userInput) {
        Scanner sc = new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));
        when(bookDao.getById(1L)).thenReturn(new Book(1L, "Name", 1L, 1L));
        service.update(sc);
        verify(bookDao).insert(new Book(1L, "Name", 1L, 2L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1"})
    void read(String userInput) {
        Scanner sc = new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));
        when(bookDao.getById(1L)).thenReturn(new Book(1L, "Test", 1L, 1L));
        service.read(sc);
        verify(bookDao).getById(1L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1"})
    void delete(String userInput) {
        Scanner sc = new Scanner(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));
        when(bookDao.getById(1L)).thenReturn(new Book(1L, "Test", 1L, 1L));
        service.delete(sc);
        verify(bookDao).deleteById(1L);
    }
}