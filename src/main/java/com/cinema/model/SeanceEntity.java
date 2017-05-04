package com.cinema.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
public class SeanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idSeance;

    private Byte threeDim;
    private Byte lector;
    private Byte subtitles;
    private Byte dubbing;

    @OneToOne
    private MovieEntity movie;

    public Integer getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(Integer idSeance) {
        this.idSeance = idSeance;
    }

    public Byte getThreeDim() {
        return threeDim;
    }

    public void setThreeDim(Byte threeDim) {
        this.threeDim = threeDim;
    }

    public Byte getLector() {
        return lector;
    }

    public void setLector(Byte lector) {
        this.lector = lector;
    }

    public Byte getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(Byte subtitles) {
        this.subtitles = subtitles;
    }

    public Byte getDubbing() {
        return dubbing;
    }

    public void setDubbing(Byte dubbing) {
        this.dubbing = dubbing;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public CinemaHallEntity getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHallEntity cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public Integer getPriceIdPrice() {
        return priceIdPrice;
    }

    public void setPriceIdPrice(Integer priceIdPrice) {
        this.priceIdPrice = priceIdPrice;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @OneToOne
    private CinemaHallEntity cinemaHall;

    private Integer priceIdPrice;

    private Timestamp date;


}
