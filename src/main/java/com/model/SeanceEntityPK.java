package com.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by msoch_000 on 06-04-2017.
 */
public class SeanceEntityPK implements Serializable {
    private int idSeance;
    private int movieIdMovie;
    private int cinemaHallIdCinemaHall;
    private int priceIdPrice;

    @Column(name = "id_seance")
    @Id
    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }

    @Column(name = "MOVIE_id_movie")
    @Id
    public int getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(int movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }

    @Column(name = "CINEMA_HALL_id_cinema_hall")
    @Id
    public int getCinemaHallIdCinemaHall() {
        return cinemaHallIdCinemaHall;
    }

    public void setCinemaHallIdCinemaHall(int cinemaHallIdCinemaHall) {
        this.cinemaHallIdCinemaHall = cinemaHallIdCinemaHall;
    }

    @Column(name = "PRICE_id_price")
    @Id
    public int getPriceIdPrice() {
        return priceIdPrice;
    }

    public void setPriceIdPrice(int priceIdPrice) {
        this.priceIdPrice = priceIdPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeanceEntityPK that = (SeanceEntityPK) o;

        if (idSeance != that.idSeance) return false;
        if (movieIdMovie != that.movieIdMovie) return false;
        if (cinemaHallIdCinemaHall != that.cinemaHallIdCinemaHall) return false;
        if (priceIdPrice != that.priceIdPrice) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeance;
        result = 31 * result + movieIdMovie;
        result = 31 * result + cinemaHallIdCinemaHall;
        result = 31 * result + priceIdPrice;
        return result;
    }
}
