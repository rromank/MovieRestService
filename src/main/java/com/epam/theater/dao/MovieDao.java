package com.epam.theater.dao;

import com.epam.theater.domain.Movie;

import java.util.List;

public interface MovieDao {

    public Movie add(Movie movie);

    public List<Movie> getAll();

    public Movie getById(int id);

    public Movie update(Movie movie);

    public boolean delete(int id);

    public boolean deleteAll();

}