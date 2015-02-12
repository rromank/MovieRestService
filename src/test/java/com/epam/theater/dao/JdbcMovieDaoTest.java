package com.epam.theater.dao;

import com.epam.theater.domain.Movie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class JdbcMovieDaoTest {

    private final static Logger logger = LoggerFactory.getLogger(JdbcMovieDaoTest.class);

    private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private static final String MOVIE_ONE_TITLE = "movie one";
    private static final int MOVIE_ONE_TOTAL_SEATS = 20;
    private static final int MOVIE_ONE_FREE_SEATS = MOVIE_ONE_TOTAL_SEATS;
    private static final Date MOVIE_ONE_DATE = getDate("10.10.2010 10:10:10");
    private Movie movie;

    @Autowired
    private JdbcMovieDao movieDao;

    @Before
    public void setUp() {
        movie = new Movie(MOVIE_ONE_TITLE, MOVIE_ONE_TOTAL_SEATS, MOVIE_ONE_FREE_SEATS, MOVIE_ONE_DATE);
    }

    @After
    public void tearDown() {
        movieDao.deleteAll();
    }

    @Test
    public void shouldSizeEqualsZeroWhenGetAllWithEmptyDataBase() {
        int expectedSize = 0;
        List<Movie> movies = movieDao.getAll();
        assertEquals(expectedSize, movies.size());
    }

    @Test
    public void shouldExpectedSizeWhenGetAllWithNotEmptyDataBase() {
        int expectedSize = 1;
        movieDao.add(movie);

        List<Movie> movies = movieDao.getAll();
        assertEquals(expectedSize, movies.size());
    }


    @Test
    public void shouldEqualsWhenGetByIdWithSameMovie() {
        movie = movieDao.add(movie);
        Movie movieFromDao = movieDao.getById(movie.getId());

        assertEquals(movie, movieFromDao);
    }

    @Test
    public void shouldUpdatedTitleWhenUpdateWithNewTitle() {
        String newTitle = "New Title";
        movie = movieDao.add(movie);

        movie.setTitle(newTitle);
        movieDao.update(movie);

        Movie updatedMovie = movieDao.getById(movie.getId());
        assertEquals(newTitle, updatedMovie.getTitle());
    }

    @Test
    public void shouldTrueWhenDeleteWithKeptMovie() {
        movie = movieDao.add(movie);

        assertTrue(movieDao.delete(movie.getId()));
    }

    @Test
    public void shouldFalseWhenDeleteWithNotKeptMovie() {
        int notKeptId = 5;
        assertFalse(movieDao.delete(notKeptId));
    }

    @Test
    public void shouldTrueWhenDeleteAllWithSomeCountOfMovies() {
        movieDao.add(movie);
        movieDao.add(movie);

        assertTrue(movieDao.deleteAll());
    }

    @Test
    public void shouldSizeEqualsZeroWhenDeleteAllWithSomeCountOfMovies() {
        int expectedSize = 0;
        movieDao.add(movie);
        movieDao.add(movie);

        movieDao.deleteAll();

        List<Movie> movies = movieDao.getAll();
        assertEquals(expectedSize, movies.size());
    }

    private static Date getDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.error("Can't parse date");
        }
        return date;
    }

}