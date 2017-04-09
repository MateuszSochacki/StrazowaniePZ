package com.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by msoch_000 on 09-04-2017.
 */
@Entity
@Table(name = "view_hall", schema = "sql11167212")

public class ViewHallEntity {
    private int idSeat;
    private Integer row;
    private Integer number;
    private int cinemaHallIdCinemaHall;
    private int idCinemaHall;
    private Integer maxSeats;
    private Integer seatsTaken;

    @Basic
    @Column(name = "id_seat")
    public int getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(int idSeat) {
        this.idSeat = idSeat;
    }

    @Basic
    @Column(name = "row")
    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "CINEMA_HALL_id_cinema_hall")
    public int getCinemaHallIdCinemaHall() {
        return cinemaHallIdCinemaHall;
    }

    public void setCinemaHallIdCinemaHall(int cinemaHallIdCinemaHall) {
        this.cinemaHallIdCinemaHall = cinemaHallIdCinemaHall;
    }

    @Basic
    @Column(name = "id_cinema_hall")
    public int getIdCinemaHall() {
        return idCinemaHall;
    }

    public void setIdCinemaHall(int idCinemaHall) {
        this.idCinemaHall = idCinemaHall;
    }

    @Basic
    @Column(name = "max_seats")
    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    @Basic
    @Column(name = "seats_taken")
    public Integer getSeatsTaken() {
        return seatsTaken;
    }

    public void setSeatsTaken(Integer seatsTaken) {
        this.seatsTaken = seatsTaken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewHallEntity that = (ViewHallEntity) o;

        if (idSeat != that.idSeat) return false;
        if (cinemaHallIdCinemaHall != that.cinemaHallIdCinemaHall) return false;
        if (idCinemaHall != that.idCinemaHall) return false;
        if (row != null ? !row.equals(that.row) : that.row != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (maxSeats != null ? !maxSeats.equals(that.maxSeats) : that.maxSeats != null) return false;
        if (seatsTaken != null ? !seatsTaken.equals(that.seatsTaken) : that.seatsTaken != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeat;
        result = 31 * result + (row != null ? row.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + cinemaHallIdCinemaHall;
        result = 31 * result + idCinemaHall;
        result = 31 * result + (maxSeats != null ? maxSeats.hashCode() : 0);
        result = 31 * result + (seatsTaken != null ? seatsTaken.hashCode() : 0);
        return result;
    }
}
