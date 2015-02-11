package com.epam.theater.service;

import com.epam.theater.dao.MovieDao;
import com.epam.theater.dao.TicketDao;
import com.epam.theater.domain.Movie;
import com.epam.theater.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    @Qualifier(value = "ticketDao")
    private TicketDao ticketDao;

    @Autowired
    @Qualifier(value = "movieDao")
    private MovieDao movieDao;

    @Override
    public Ticket buy(int movieId) {
        Movie movie = movieDao.getById(movieId);
        decreaseFreeSeats(movie);
        int unusedSeatNumber = ticketDao.getUnusedSeatNumber(movie.getId());
        Ticket ticket = new Ticket();
        ticket.setMovie(movie);
        ticket.setSeatNumber(unusedSeatNumber);

        return ticketDao.add(ticket);
    }

    @Override
    public boolean delete(int id) {
        Ticket ticket = ticketDao.getById(id);
        Movie movie = ticket.getMovie();
        increaseFreeSeats(movie);
        return ticketDao.delete(id);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    private void decreaseFreeSeats(Movie movie) {
        if (movie.getFreeSeats() > 0) {
            movie.setFreeSeats(movie.getFreeSeats() - 1);
            movieDao.update(movie);
        }
    }

    private void increaseFreeSeats(Movie movie) {
        if (movie.getFreeSeats() < movie.getTotalSeats()) {
            movie.setFreeSeats(movie.getFreeSeats() + 1);
            movieDao.update(movie);
        }
   }

}