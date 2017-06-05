package com.cinema.services;

import com.cinema.model.CinemaHallEntity;
import com.cinema.model.SeanceEntity;
import com.cinema.model.SeatEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by msoch_000 on 02-05-2017.
 */
public interface SeatRepository extends CrudRepository<SeatEntity, Integer> {

    //List<SeatEntity> findByCinemaHall(CinemaHallEntity cinemaHallEntity);
    List<SeatEntity> findAll();
    List<SeatEntity> findBySeanceEntity(SeanceEntity seanceEntity);

}
