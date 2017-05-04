package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
public class SeatEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer idSeat;

    private Integer number;
    private Integer row;

    public Integer getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(Integer idSeat) {
        this.idSeat = idSeat;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public CinemaHallEntity getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHallEntity cinemaHallIdCinemaHall) {
        this.cinemaHall = cinemaHallIdCinemaHall;
    }

    @OneToOne(fetch = FetchType.EAGER)
    private CinemaHallEntity cinemaHall;


}
