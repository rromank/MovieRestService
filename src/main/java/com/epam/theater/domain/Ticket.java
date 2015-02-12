package com.epam.theater.domain;

public class Ticket implements Persistable {

    private int id;
    private Movie movie;
    private int seatNumber;

    public Ticket() {}

    public Ticket(Movie movie, int seatNumber) {
        this.movie = movie;
        this.seatNumber = seatNumber;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return seatNumber == ticket.seatNumber && !(movie != null ? !movie.equals(ticket.movie) : ticket.movie != null);

    }

    @Override
    public int hashCode() {
        int result = movie != null ? movie.hashCode() : 0;
        result = 31 * result + seatNumber;
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "movie=" + movie +
                ", seatNumber=" + seatNumber +
                '}';
    }

}