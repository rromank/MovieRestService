package com.epam.theater.dao;

import com.epam.theater.dao.mapper.TicketMapper;
import com.epam.theater.domain.Movie;
import com.epam.theater.domain.Ticket;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTicketDao extends NamedParameterJdbcDaoSupport implements TicketDao {

    private static final String INSERT = "INSERT INTO ticket (movie_id, seat_number) VALUES (:movie_id, :seat_number)";
    private static final String SELECT_ALL = "SELECT * FROM ticket LEFT JOIN movie ON ticket.movie_id = movie.movie_id";
    private static final String DELETE_BY_ID = "DELETE FROM ticket WHERE ticket_id = :ticket_id";
    private static final String SELECT_BY_ID = "SELECT * FROM ticket LEFT JOIN movie ON ticket.movie_id = movie.movie_id WHERE ticket_id = :ticket_id";
    private static final String SELECT_UNUSED_SEAT_NUMBER = "SELECT min(unused) AS unused " +
            "FROM ( " +
            "    SELECT MIN(t1.seat_number)+1 as unused " +
            "    FROM ticket AS t1 " +
            "    WHERE NOT EXISTS (SELECT * FROM ticket AS t2 WHERE t2.seat_number = t1.seat_number+1) AND movie_id = :movie_id " +
            "    UNION SELECT 1 FROM DUAL " +
            "    WHERE NOT EXISTS (SELECT * FROM ticket WHERE seat_number = 1 AND movie_id = :movie_id) " +
            ") AS subquery";

    @Override
    public Ticket add(Ticket ticket) {
        SqlParameterSource parameterSource = getTicketParams(ticket);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getNamedParameterJdbcTemplate().update(INSERT, parameterSource, keyHolder);

        int id = getGeneratedId(keyHolder);
        return cloneAndSetId(ticket, id);
    }

    @Override
    public List<Ticket> getAll() {
        return getJdbcTemplate().query(SELECT_ALL, new TicketMapper());
    }

    @Override
    public boolean delete(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("ticket_id", id);
        int deletedCount = getNamedParameterJdbcTemplate().update(DELETE_BY_ID, parameterSource);
        return deletedCount > 0;
    }

    @Override
    public Ticket getById(int id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("ticket_id", id);
        return getNamedParameterJdbcTemplate().queryForObject(SELECT_BY_ID, parameterSource, new TicketMapper());
    }

    @Override
    public int getUnusedSeatNumber(int movieId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("movie_id", movieId);
        return getNamedParameterJdbcTemplate().queryForObject(SELECT_UNUSED_SEAT_NUMBER, parameterSource, Integer.class);
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