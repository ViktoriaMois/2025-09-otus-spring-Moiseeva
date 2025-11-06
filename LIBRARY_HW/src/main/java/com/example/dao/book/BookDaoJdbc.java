package com.example.dao.book;

import com.example.domain.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("SELECT COUNT(*) FROM books", Map.of(), Integer.class);
    }

    @Override
    public void insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", book.getName());
        params.addValue("id_author", book.getAuthor());
        params.addValue("id_genre", book.getGenre());

        namedParameterJdbcOperations.update("INSERT INTO books (name, id_author, id_genre) VALUES (:name, :id_author, :id_genre)", params, keyHolder, new String[]{"id_book"});

        book.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id_book", id);
        return namedParameterJdbcOperations.queryForObject("SELECT * FROM books WHERE id_book = :id_book", params, new BookDaoJdbc.BookMapper());
    }

    @Override
    public Book getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return namedParameterJdbcOperations.queryForObject("SELECT * FROM books WHERE name = :name", params, new BookDaoJdbc.BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("SELECT * FROM books", new BookDaoJdbc.BookMapper());
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcOperations.update("DELETE FROM books", Map.of());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id_book", id);
        namedParameterJdbcOperations.update("DELETE FROM books WHERE id_book = :id_book", params);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id_book");
            String name = resultSet.getString("name");
            long author = resultSet.getLong("id_author");
            long genre = resultSet.getLong("id_genre");
            return new Book(id, name, author, genre);
        }
    }
}
