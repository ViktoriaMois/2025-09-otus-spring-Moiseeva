package com.example.dao.author;

import com.example.domain.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("SELECT COUNT(*) FROM authors", Map.of(), Integer.class);
    }

    @Override
    public void insert(Author author) {
        namedParameterJdbcOperations.update("INSERT INTO authors (id_author, full_name) VALUES (:id_author, :full_name)",
                Map.of("id_author", author.getId(), "full_name", author.getFullName()));
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id_author", id);
        return namedParameterJdbcOperations.queryForObject("SELECT * FROM authors WHERE id_author = :id_author", params, new AuthorMapper());
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("full_name", name);
        return namedParameterJdbcOperations.queryForObject("SELECT * FROM authors WHERE full_name = :full_name", params, new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("SELECT * FROM authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id_author", id);
        namedParameterJdbcOperations.update("DELETE FROM authors WHERE id_author = :id_author", params);
    }


    @Override
    public void deleteAll() {
        namedParameterJdbcOperations.update("DELETE FROM authors", Map.of());
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id_author");
            String fullName = resultSet.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
