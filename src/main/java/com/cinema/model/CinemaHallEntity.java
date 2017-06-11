package com.cinema.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by msoch_000 on 02-05-2017.
 * Stores the name of the file where the hall appearance is stored.
 */
@Entity
public class CinemaHallEntity {

    public Integer getIdCinemaHall() {
        return idCinemaHall;
    }

    public void setIdCinemaHall(Integer idCinemaHall) {
        this.idCinemaHall = idCinemaHall;
    }

    public int getCinemaHallType() {
        return cinemaHallType;
    }

    public void setCinemaHallType(int cinemaHallType) {
        this.cinemaHallType = cinemaHallType;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Integer getSeatsTaken() {
        return seatsTaken;
    }

    public void setSeatsTaken(Integer seatsTaken) {
        this.seatsTaken = seatsTaken;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCinemaHall;

    private int cinemaHallType;
    private Integer maxSeats;
    private Integer seatsTaken;


}
