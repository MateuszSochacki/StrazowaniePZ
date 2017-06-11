package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 * Stores information about seats - taken, free etc.
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

//    public CinemaHallEntity getCinemaHall() {
//        return cinemaHall;
//    }
//
//    public void setCinemaHall(CinemaHallEntity cinemaHallIdCinemaHall) {
//        this.cinemaHall = cinemaHallIdCinemaHall;
//    }
//
//    @OneToOne(fetch = FetchType.EAGER)
//    private CinemaHallEntity cinemaHall;

    public SeanceEntity getSeanceEntity() {
        return seanceEntity;
    }

    public void setSeanceEntity(SeanceEntity seanceEntity) {
        this.seanceEntity = seanceEntity;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private SeanceEntity seanceEntity;


}
