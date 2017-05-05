package com.cinema.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by msoch_000 on 02-05-2017.
 */
public class SeatEntityPK implements Serializable {
    private Integer idSeat;
    private Integer cinemaHallIdCinemaHall;

    @GeneratedValue
    @Column(name = "ID_SEAT", nullable = false)
    @Id
    public Integer getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(Integer idSeat) {
        this.idSeat = idSeat;
    }

    @Column(name = "CINEMA_HALL_ID_CINEMA_HALL", nullable = false)
    @Id
    public Integer getCinemaHallIdCinemaHall() {
        return cinemaHallIdCinemaHall;
    }

    public void setCinemaHallIdCinemaHall(Integer cinemaHallIdCinemaHall) {
        this.cinemaHallIdCinemaHall = cinemaHallIdCinemaHall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatEntityPK that = (SeatEntityPK) o;

        if (idSeat != null ? !idSeat.equals(that.idSeat) : that.idSeat != null) return false;
        if (cinemaHallIdCinemaHall != null ? !cinemaHallIdCinemaHall.equals(that.cinemaHallIdCinemaHall) : that.cinemaHallIdCinemaHall != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeat != null ? idSeat.hashCode() : 0;
        result = 31 * result + (cinemaHallIdCinemaHall != null ? cinemaHallIdCinemaHall.hashCode() : 0);
        return result;
    }
}
