package com.epam.theater.dao;

import com.epam.theater.domain.Movie;
import com.epam.theater.domain.Ticket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class JdbcTicketDaoTest {

    private static final int SEAT_NUMBER = 1;
    private static final int MOVIE_ID = 1;
    private static final Movie MOVIE = new Movie();
    private Ticket ticket;

    @Autowired
    private JdbcTicketDao ticketDao;

    @Before
    public void setUp() {
        ticket = new Ticket(MOVIE, SEAT_NUMBER);
    }

    @After
    public void tearDown() {
        ticketDao.deleteAll();
    }

    private Movie getMovieWithId() {
        Movie movie = mock(Movie.class);
        when(movie.getId()).thenReturn(MOVIE_ID);
        return movie;
    }

    @Test
    public void shouldSizeEqualsZeroWhenGetAllWithEmptyDataBase() {
        int expectedSize = 0;
        List<Ticket> tickets = ticketDao.getAll();
        assertEquals(expectedSize, tickets.size());
    }

    @Test
    public void shouldExpectedSizeWhenGetAllWithNotEmptyDataBase() {
        int expectedSize = 1;
        ticketDao.add(ticket);

        List<Ticket> tickets = ticketDao.getAll();
        assertEquals(expectedSize, tickets.size());
    }

    @Test
    public void shouldTrueWhenDeleteWithKeptTicket() {
        ticket = ticketDao.add(ticket);

        assertTrue(ticketDao.delete(ticket.getId()));
    }

    @Test
    public void shouldFalseWhenDeleteWithNotKeptTicket() {
        int notKeptId = 5;

        assertFalse(ticketDao.delete(notKeptId));
    }

    @Test
    public void shouldEqualsWhenGetByIdWithSameTicket() {
        ticket = ticketDao.add(ticket);
        Ticket ticketFromDao = ticketDao.getById(ticket.getId());

        assertEquals(ticket, ticketFromDao);
    }

    @Test
    public void shouldTrueWhenDeleteAllWithSomeCountOfTickets() {
        ticketDao.add(ticket);
        ticketDao.add(ticket);

        assertTrue(ticketDao.deleteAll());
    }

    @Test
    public void shouldSizeEqualsZeroWhenDeleteAllWithSomeCountOfMovies() {
        int expectedSize = 0;
        ticketDao.add(ticket);
        ticketDao.add(ticket);

        ticketDao.deleteAll();

        List<Ticket> tickets = ticketDao.getAll();
        assertEquals(expectedSize, tickets.size());
    }

    @Test
    public void shouldExpectedSeatNumberWhenGetUnusedSeatNumberWithFirstUnused() {
        int expectedSeatNumber = 1;
        int seatNumber = 2;

        Movie movie = getMovieWithId();
        setupTicket(movie, seatNumber);

        assertEquals(expectedSeatNumber, ticketDao.getUnusedSeatNumber(movie.getId()));
    }

    @Test
    public void shouldExpectedSeatNumberWhenGetUnusedSeatNumberWithSecondUnused() {
        int expectedSeatNumber = 2;
        int seatNumber = 1;

        Movie movie = getMovieWithId();
        setupTicket(movie, seatNumber);

        assertEquals(expectedSeatNumber, ticketDao.getUnusedSeatNumber(movie.getId()));
    }

    private void setupTicket(Movie movie, int seatNumber) {
        ticket.setMovie(movie);
        ticket.setSeatNumber(seatNumber);
        ticketDao.add(ticket);
    }

}