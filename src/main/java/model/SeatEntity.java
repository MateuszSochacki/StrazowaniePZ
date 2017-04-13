package model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 10-04-2017.
 */
@Entity
@Table(name = "SEAT")
@IdClass(SeatEntityPK.class)
public class SeatEntity {
    private int idSeat;
    private Integer row;
    private Integer number;
    private int cinemaHallIdCinemaHall;

    @Id
    @Column(name = "ID_SEAT")
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
    @Column(name = "NUMBER")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Id
    @Column(name = "CINEMA_HALL_ID_CINEMA_HALL")
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

        SeatEntity that = (SeatEntity) o;

        if (idSeat != that.idSeat) return false;
        if (cinemaHallIdCinemaHall != that.cinemaHallIdCinemaHall) return false;
        if (row != null ? !row.equals(that.row) : that.row != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSeat;
        result = 31 * result + (row != null ? row.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + cinemaHallIdCinemaHall;
        return result;
    }
}
