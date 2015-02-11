package com.epam.theater.service;

import com.epam.theater.dao.MovieDao;
import com.epam.theater.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    @Qualifier(value = "movieDao")
    private MovieDao movieDao;

    @Override
    public Movie add(Movie movie) {
        return movieDao.add(movie);
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public Movie getById(int id) {
        return movieDao.getById(id);
    }

    @Override
    public Movie update(Movie movie) {
        return movieDao.update(movie);
    }

    @Override
    public boolean delete(int id) {
        return movieDao.delete(id);
    }

}