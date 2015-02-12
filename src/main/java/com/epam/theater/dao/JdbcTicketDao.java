package com.epam.theater.dao;

import com.epam.theater.dao.mapper.TicketMapper;
import com.epam.theater.domain.Movie;
import com.epam.theater.domain.Ticket;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTicketDao extends NamedParameterJdbcDaoSupport implements TicketDao {

    @Resource(name="queries")
    private Map<String, String> queries;

    @Override
    public Ticket add(Ticket ticket) {
        SqlParameterSource parameterSource = getTicketParams(ticket);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getNamedParameterJdbcTemplate().update(queries.get("TICKET_INSERT"), parameterSource, keyHolder);

        int id = getGeneratedId(keyHolder);
        return cloneAndSetId(ticket, id);
    }

    @Override
    public List<Ticket> getAll() {
        return getJdbcTemplate().query(queries.get("TICKET_SELECT_ALL"), new TicketMapper());
    }

    @Override
    public boolean delete(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("ticket_id", id);
        int deletedCount = getNamedParameterJdbcTemplate().update(queries.get("TICKET_DELETE_BY_ID"), parameterSource);
        return deletedCount > 0;
    }

    @Override
    public Ticket getById(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("ticket_id", id);
        return getNamedParameterJdbcTemplate().queryForObject(queries.get("TICKET_SELECT_BY_ID"), parameterSource, new TicketMapper());
    }

    @Override
    public int getUnusedSeatNumber(int movieId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("movie_id", movieId);
        return getNamedParameterJdbcTemplate().queryForObject(queries.get("TICKET_SELECT_UNUSED_SEAT_NUMBER"), parameterSource, Integer.class);
    }

    @Override
    public boolean deleteAll() {
        int deletedCount = getJdbcTemplate().update(queries.get("TICKET_DELETE_ALL"));
        return deletedCount > 0;
    }

    private SqlParameterSource getTicketParams(Ticket ticket) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", ticket.getId());

        Movie movie = ticket.getMovie();
        params.put("movie_id", movie.getId());
        params.put("seat_number", ticket.getSeatNumber());

        return new MapSqlParameterSource(params);
    }

    private Ticket cloneAndSetId(Ticket ticket, int id) {
        Ticket ticketClone = new Ticket();
        ticketClone.setId(id);
        ticketClone.setMovie(ticket.getMovie());
        ticketClone.setSeatNumber(ticket.getSeatNumber());
        return ticketClone;
    }

    private int getGeneratedId(KeyHolder keyHolder) {
        Number id = keyHolder.getKey();
        return id.intValue();
    }

}