package com.cinema.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by msoch_000 on 10-04-2017.
 */
public class SeatEntityPK implements Serializable {
    private int idSeat;
    private int cinemaHallIdCinemaHall;

    @Column(name = "ID_SEAT")
    @Id
    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }

    @Column(name = "CINEMA_HALL_ID_CINEMA_HALL")
    @Id
    public int getCinemaHallIdCinemaHall() {
        return cinemaHallIdCinemaHall;
    }

    public void setCinemaHallIdCinemaHall(int cinemaHallIdCinemaHall) {
        this.cinemaHallIdCinemaHall = cinemaHallIdCinemaHall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatEntityPK that = (SeatEntityPK) o;

        if (idSeat != that.idSeat) return false;
        if (cinemaHallIdCinemaHall != that.cinemaHallIdCinemaHall) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeat;
        result = 31 * result + cinemaHallIdCinemaHall;
        return result;
    }
}
