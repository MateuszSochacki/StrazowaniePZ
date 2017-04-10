package com.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by msoch_000 on 10-04-2017.
 */
@Entity
@Table(name = "SEANCE", schema = "PUBLIC", catalog = "DOCUMENTS")
@IdClass(SeanceEntityPK.class)
public class SeanceEntity {
    private int idSeance;
    private Timestamp date;
    private Byte threeDimension;
    private Byte lector;
    private Byte subtitles;
    private Byte dubbing;
    private int movieIdMovie;
    private int cinemaHallIdCinemaHall;
    private int priceIdPrice;

    @Id
    @Column(name = "ID_SEANCE")
    public int getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(int idSeance) {
        this.idSeance = idSeance;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "THREE_DIMENSION")
    public Byte getThreeDimension() {
        return threeDimension;
    }

    public void setThreeDimension(Byte threeDimension) {
        this.threeDimension = threeDimension;
    }

    @Basic
    @Column(name = "LECTOR")
    public Byte getLector() {
        return lector;
    }

    public void setLector(Byte lector) {
        this.lector = lector;
    }

    @Basic
    @Column(name = "SUBTITLES")
    public Byte getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(Byte subtitles) {
        this.subtitles = subtitles;
    }

    @Basic
    @Column(name = "DUBBING")
    public Byte getDubbing() {
        return dubbing;
    }

    public void setDubbing(Byte dubbing) {
        this.dubbing = dubbing;
    }

    @Id
    @Column(name = "MOVIE_ID_MOVIE")
    public int getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(int movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }

    @Id
    @Column(name = "CINEMA_HALL_ID_CINEMA_HALL")
    public int getCinemaHallIdCinemaHall() {
        return cinemaHallIdCinemaHall;
    }

    public void setCinemaHallIdCinemaHall(int cinemaHallIdCinemaHall) {
        this.cinemaHallIdCinemaHall = cinemaHallIdCinemaHall;
    }

    @Id
    @Column(name = "PRICE_ID_PRICE")
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

        SeanceEntity that = (SeanceEntity) o;

        if (idSeance != that.idSeance) return false;
        if (movieIdMovie != that.movieIdMovie) return false;
        if (cinemaHallIdCinemaHall != that.cinemaHallIdCinemaHall) return false;
        if (priceIdPrice != that.priceIdPrice) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (threeDimension != null ? !threeDimension.equals(that.threeDimension) : that.threeDimension != null)
            return false;
        if (lector != null ? !lector.equals(that.lector) : that.lector != null) return false;
        if (subtitles != null ? !subtitles.equals(that.subtitles) : that.subtitles != null) return false;
        if (dubbing != null ? !dubbing.equals(that.dubbing) : that.dubbing != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeance;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (threeDimension != null ? threeDimension.hashCode() : 0);
        result = 31 * result + (lector != null ? lector.hashCode() : 0);
        result = 31 * result + (subtitles != null ? subtitles.hashCode() : 0);
        result = 31 * result + (dubbing != null ? dubbing.hashCode() : 0);
        result = 31 * result + movieIdMovie;
        result = 31 * result + cinemaHallIdCinemaHall;
        result = 31 * result + priceIdPrice;
        return result;
    }
}
