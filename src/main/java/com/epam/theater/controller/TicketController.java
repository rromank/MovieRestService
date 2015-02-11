package com.epam.theater.controller;

import com.epam.theater.domain.Ticket;
import com.epam.theater.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    @Qualifier(value = "ticketService")
    private TicketService ticketService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Ticket> getAll() {
        return ticketService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id") int id) {
        boolean isDeleted = ticketService.delete(id);
        return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
    }

    @RequestMapping(value = "/buy/{movie_id}", method = RequestMethod.GET)
    public Ticket buy(@PathVariable(value = "movie_id") int movieId) {
        return ticketService.buy(movieId);
    }

}