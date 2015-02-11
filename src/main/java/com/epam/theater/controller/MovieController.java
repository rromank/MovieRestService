package com.epam.theater.controller;

import com.epam.theater.domain.Movie;
import com.epam.theater.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    @Qualifier(value = "movieService")
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Movie getById(@PathVariable(value = "id") int id) {
        return movieService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Movie add(@RequestBody Movie movie) {
        return movieService.add(movie);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Movie update(@RequestBody Movie movie) {
        return movieService.update(movie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") int id) {
        boolean isDeleted = movieService.delete(id);
        return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
    }

}