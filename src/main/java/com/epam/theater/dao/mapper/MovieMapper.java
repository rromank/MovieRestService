package com.epam.theater.dao.mapper;

import com.epam.theater.domain.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt("movie_id"));
        movie.setTitle(resultSet.getString("title"));
        movie.setTotalSeats(resultSet.getInt("total_seats"));
        movie.setFreeSeats(resultSet.getInt("free_seats"));
        movie.setDate(resultSet.getDate("date"));
        return movie;
    }

}