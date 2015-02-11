package com.epam.theater.service;

import com.epam.theater.domain.Ticket;

import java.util.List;

public interface TicketService {

    public Ticket buy(int movieId);

    public boolean delete(int id);

    public List<Ticket> getAll();

}