package com.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 09-04-2017.
 */
@Entity
@Table(name = "CINEMA_HALL", schema = "sql11167212")
public class CinemaHallEntity {
    private int idCinemaHall;
    private Integer maxSeats;
    private Integer seatsTaken;

    @Id
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

        CinemaHallEntity that = (CinemaHallEntity) o;

        if (idCinemaHall != that.idCinemaHall) return false;
        if (maxSeats != null ? !maxSeats.equals(that.maxSeats) : that.maxSeats != null) return false;
        if (seatsTaken != null ? !seatsTaken.equals(that.seatsTaken) : that.seatsTaken != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCinemaHall;
        result = 31 * result + (maxSeats != null ? maxSeats.hashCode() : 0);
        result = 31 * result + (seatsTaken != null ? seatsTaken.hashCode() : 0);
        return result;
    }
}
