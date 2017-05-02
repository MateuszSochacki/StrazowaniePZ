package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
@Table(name = "CINEMA_HALL", schema = "PUBLIC", catalog = "DATABASE")
public class CinemaHallEntity {
    private Integer idCinemaHall;
    private Integer maxSeats;
    private Integer seatsTaken;

    @Id
    @Column(name = "ID_CINEMA_HALL")
    public Integer getIdCinemaHall() {
        return idCinemaHall;
    }

    public void setIdCinemaHall(Integer idCinemaHall) {
        this.idCinemaHall = idCinemaHall;
    }

    @Basic
    @Column(name = "MAX_SEATS")
    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    @Basic
    @Column(name = "SEATS_TAKEN")
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

        CinemaHallEntity that = (CinemaHallEntity) o;

        if (idCinemaHall != null ? !idCinemaHall.equals(that.idCinemaHall) : that.idCinemaHall != null) return false;
        if (maxSeats != null ? !maxSeats.equals(that.maxSeats) : that.maxSeats != null) return false;
        if (seatsTaken != null ? !seatsTaken.equals(that.seatsTaken) : that.seatsTaken != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCinemaHall != null ? idCinemaHall.hashCode() : 0;
        result = 31 * result + (maxSeats != null ? maxSeats.hashCode() : 0);
        result = 31 * result + (seatsTaken != null ? seatsTaken.hashCode() : 0);
        return result;
    }
}
