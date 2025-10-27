package com.example.dao.genre;

import com.example.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("SELECT COUNT(*) FROM genres", Map.of(), Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        namedParameterJdbcOperations.update("INSERT INTO genres (id_genre, name) VALUES (:id_genre, :name)",
                Map.of("id_genre", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id_genre", id);
        return namedParameterJdbcOperations.queryForObject("SELECT * FROM genres WHERE id_genre = :id_genre", params, new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return namedParameterJdbcOperations.queryForObject("SELECT * FROM genres WHERE name = :name", params, new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("SELECT * FROM genres", new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcOperations.update("DELETE FROM genres", Map.of());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id_genre", id);
        namedParameterJdbcOperations.update("DELETE FROM genres WHERE id_genre = :id_genre", params);
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id_genre");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
