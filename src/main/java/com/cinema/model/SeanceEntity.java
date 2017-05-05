package com.cinema.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
@Table(name = "SEANCE", schema = "PUBLIC", catalog = "DATABASE")
@IdClass(SeanceEntityPK.class)
public class SeanceEntity {
    private Integer idSeance;
    private Byte threeDim;
    private Byte lector;
    private Byte subtitles;
    private Byte dubbing;
    private Integer movieIdMovie;
    private Integer cinemaHallIdCinemaHall;
    private Integer priceIdPrice;
    private Timestamp date;

    @Id
    @GeneratedValue
    @Column(name = "ID_SEANCE", nullable = false)
    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    @Basic
    @Column(name = "THREE_DIM", nullable = true)
    public Byte getThreeDim() {
        return threeDim;
    }

    public void setThreeDim(Byte threeDim) {
        this.threeDim = threeDim;
    }

    @Basic
    @Column(name = "LECTOR", nullable = true)
    public Byte getLector() {
        return lector;
    }

    public void setLector(Byte lector) {
        this.lector = lector;
    }

    @Basic
    @Column(name = "SUBTITLES", nullable = true)
    public Byte getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(Byte subtitles) {
        this.subtitles = subtitles;
    }

    @Basic
    @Column(name = "DUBBING", nullable = true)
    public Byte getDubbing() {
        return dubbing;
    }

    public void setDubbing(Byte dubbing) {
        this.dubbing = dubbing;
    }

    @Id
    @Column(name = "MOVIE_ID_MOVIE", nullable = false)
    public Integer getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(Integer movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }

    @Id
    @Column(name = "CINEMA_HALL_ID_CINEMA_HALL", nullable = false)
    public Integer getCinemaHallIdCinemaHall() {
        return cinemaHallIdCinemaHall;
    }

    public void setCinemaHallIdCinemaHall(Integer cinemaHallIdCinemaHall) {
        this.cinemaHallIdCinemaHall = cinemaHallIdCinemaHall;
    }

    @Id
    @Column(name = "PRICE_ID_PRICE", nullable = false)
    public Integer getPriceIdPrice() {
        return priceIdPrice;
    }

    public void setPriceIdPrice(Integer priceIdPrice) {
        this.priceIdPrice = priceIdPrice;
    }

    @Basic
    @Column(name = "DATE", nullable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeanceEntity that = (SeanceEntity) o;

        if (idSeance != null ? !idSeance.equals(that.idSeance) : that.idSeance != null) return false;
        if (threeDim != null ? !threeDim.equals(that.threeDim) : that.threeDim != null) return false;
        if (lector != null ? !lector.equals(that.lector) : that.lector != null) return false;
        if (subtitles != null ? !subtitles.equals(that.subtitles) : that.subtitles != null) return false;
        if (dubbing != null ? !dubbing.equals(that.dubbing) : that.dubbing != null) return false;
        if (movieIdMovie != null ? !movieIdMovie.equals(that.movieIdMovie) : that.movieIdMovie != null) return false;
        if (cinemaHallIdCinemaHall != null ? !cinemaHallIdCinemaHall.equals(that.cinemaHallIdCinemaHall) : that.cinemaHallIdCinemaHall != null)
            return false;
        if (priceIdPrice != null ? !priceIdPrice.equals(that.priceIdPrice) : that.priceIdPrice != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeance != null ? idSeance.hashCode() : 0;
        result = 31 * result + (threeDim != null ? threeDim.hashCode() : 0);
        result = 31 * result + (lector != null ? lector.hashCode() : 0);
        result = 31 * result + (subtitles != null ? subtitles.hashCode() : 0);
        result = 31 * result + (dubbing != null ? dubbing.hashCode() : 0);
        result = 31 * result + (movieIdMovie != null ? movieIdMovie.hashCode() : 0);
        result = 31 * result + (cinemaHallIdCinemaHall != null ? cinemaHallIdCinemaHall.hashCode() : 0);
        result = 31 * result + (priceIdPrice != null ? priceIdPrice.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
