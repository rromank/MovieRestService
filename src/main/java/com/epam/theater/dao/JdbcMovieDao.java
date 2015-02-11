package com.epam.theater.dao;

import com.epam.theater.dao.mapper.MovieMapper;
import com.epam.theater.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcMovieDao extends NamedParameterJdbcDaoSupport implements MovieDao {

    private static final String INSERT = "INSERT INTO movie (title, total_seats, free_seats, date) VALUES (:title, :total_seats, :free_seats, :date)";
    private static final String SELECT_ALL = "SELECT * FROM movie";
    private static final String SELECT_BY_ID = "SELECT * FROM movie WHERE movie_id = :movie_id";
    private static final String UPDATE_BY_ID = "UPDATE movie SET title = :title, total_seats = :total_seats, free_seats = :free_seats, date = :date WHERE movie_id = :movie_id";
    private static final String DELETE_BY_ID = "DELETE FROM movie WHERE movie_id = :movie_id";

    @Override
    public Movie add(Movie movie) {
        SqlParameterSource parameterSource = getMovieParams(movie);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getNamedParameterJdbcTemplate().update(INSERT, parameterSource, keyHolder);

        int id = getGeneratedId(keyHolder);
        return cloneAndSetId(movie, id);
    }

    @Override
    public List<Movie> getAll() {
        return getJdbcTemplate().query(SELECT_ALL, new MovieMapper());
    }

    @Override
    public Movie getById(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("movie_id", id);
        return getNamedParameterJdbcTemplate().queryForObject(SELECT_BY_ID, parameterSource, new MovieMapper());
    }

    @Override
    public Movie update(Movie movie) {
        SqlParameterSource parameterSource = getMovieParams(movie);
        getNamedParameterJdbcTemplate().update(UPDATE_BY_ID, parameterSource);
        return movie;
    }

    @Override
    public boolean delete(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("movie_id", id);
        int deletedCount = getNamedParameterJdbcTemplate().update(DELETE_BY_ID, parameterSource);
        return deletedCount > 0;
    }

    private SqlParameterSource getMovieParams(Movie movie) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("movie_id", movie.getId());
        params.put("title", movie.getTitle());
        params.put("total_seats", movie.getTotalSeats());
        params.put("free_seats", movie.getFreeSeats());
        params.put("date", movie.getDate());

        return new MapSqlParameterSource(params);
    }

    private Movie cloneAndSetId(Movie movie, int id) {
        Movie movieClone = new Movie();
        movieClone.setId(id);
        movieClone.setTitle(movie.getTitle());
        movieClone.setFreeSeats(movie.getFreeSeats());
        movieClone.setTotalSeats(movie.getTotalSeats());
        movieClone.setDate(movie.getDate());
        return movieClone;
    }

    private int getGeneratedId(KeyHolder keyHolder) {
        Number id = keyHolder.getKey();
        return id.intValue();
    }

}