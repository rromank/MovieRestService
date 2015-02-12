package com.epam.theater.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class Movie implements Persistable {

    private int id;

    @NotNull
    private String title;

    @NotNull
    @Min(1)
    private int totalSeats;

    private int freeSeats;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="dd.MM.yyyy HH:mm:ss")
    private Date date;

    public Movie() {}

    public Movie(String title, int totalSeats, int freeSeats, Date date) {
        this.title = title;
        this.totalSeats = totalSeats;
        this.freeSeats = freeSeats;
        this.date = date;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern ="dd.MM.yyyy HH:mm:ss")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (freeSeats != movie.freeSeats) return false;
        if (totalSeats != movie.totalSeats) return false;
        if (date != null ? !date.equals(movie.date) : movie.date != null) return false;
        return !(title != null ? !title.equals(movie.title) : movie.title != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + totalSeats;
        result = 31 * result + freeSeats;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", totalSeats=" + totalSeats +
                ", freeSeats=" + freeSeats +
                ", date=" + date +
                '}';
    }

}