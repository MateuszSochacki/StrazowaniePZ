package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
@Table(name = "SEAT", schema = "PUBLIC", catalog = "DATABASE")
@IdClass(SeatEntityPK.class)
public class SeatEntity {
    private Integer idSeat;
    private Integer number;
    private Integer cinemaHallIdCinemaHall;
    private Integer row;

    @Id
    @GeneratedValue
    @Column(name = "ID_SEAT", nullable = false)
    public Integer getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(Integer idSeat) {
        this.idSeat = idSeat;
    }

    @Basic
    @Column(name = "NUMBER", nullable = true)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Id
    @Column(name = "CINEMA_HALL_ID_CINEMA_HALL", nullable = false)
    public Integer getCinemaHallIdCinemaHall() {
        return cinemaHallIdCinemaHall;
    }

    public void setCinemaHallIdCinemaHall(Integer cinemaHallIdCinemaHall) {
        this.cinemaHallIdCinemaHall = cinemaHallIdCinemaHall;
    }

    @Basic
    @Column(name = "ROW", nullable = true)
    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatEntity that = (SeatEntity) o;

        if (idSeat != null ? !idSeat.equals(that.idSeat) : that.idSeat != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (cinemaHallIdCinemaHall != null ? !cinemaHallIdCinemaHall.equals(that.cinemaHallIdCinemaHall) : that.cinemaHallIdCinemaHall != null)
            return false;
        if (row != null ? !row.equals(that.row) : that.row != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeat != null ? idSeat.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (cinemaHallIdCinemaHall != null ? cinemaHallIdCinemaHall.hashCode() : 0);
        result = 31 * result + (row != null ? row.hashCode() : 0);
        return result;
    }
}
