package com.cinema.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by msoch_000 on 02-05-2017.
 */
public class SeanceEntityPK implements Serializable {
    private Integer idSeance;
    private Integer movieIdMovie;
    private Integer cinemaHallIdCinemaHall;
    private Integer priceIdPrice;

    @GeneratedValue
    @Column(name = "ID_SEANCE", nullable = false)
    @Id
    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    @Column(name = "MOVIE_ID_MOVIE", nullable = false)
    @Id
    public Integer getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(Integer movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }

    @Column(name = "CINEMA_HALL_ID_CINEMA_HALL", nullable = false)
    @Id
    public Integer getCinemaHallIdCinemaHall() {
        return cinemaHallIdCinemaHall;
    }

    public void setCinemaHallIdCinemaHall(Integer cinemaHallIdCinemaHall) {
        this.cinemaHallIdCinemaHall = cinemaHallIdCinemaHall;
    }

    @Column(name = "PRICE_ID_PRICE", nullable = false)
    @Id
    public Integer getPriceIdPrice() {
        return priceIdPrice;
    }

    public void setPriceIdPrice(Integer priceIdPrice) {
        this.priceIdPrice = priceIdPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeanceEntityPK that = (SeanceEntityPK) o;

        if (idSeance != null ? !idSeance.equals(that.idSeance) : that.idSeance != null) return false;
        if (movieIdMovie != null ? !movieIdMovie.equals(that.movieIdMovie) : that.movieIdMovie != null) return false;
        if (cinemaHallIdCinemaHall != null ? !cinemaHallIdCinemaHall.equals(that.cinemaHallIdCinemaHall) : that.cinemaHallIdCinemaHall != null)
            return false;
        if (priceIdPrice != null ? !priceIdPrice.equals(that.priceIdPrice) : that.priceIdPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeance != null ? idSeance.hashCode() : 0;
        result = 31 * result + (movieIdMovie != null ? movieIdMovie.hashCode() : 0);
        result = 31 * result + (cinemaHallIdCinemaHall != null ? cinemaHallIdCinemaHall.hashCode() : 0);
        result = 31 * result + (priceIdPrice != null ? priceIdPrice.hashCode() : 0);
        return result;
    }
}
