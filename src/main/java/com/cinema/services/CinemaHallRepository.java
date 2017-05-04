package com.cinema.services;

import com.cinema.model.CinemaHallEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by msoch_000 on 02-05-2017.
 */
public interface CinemaHallRepository extends CrudRepository<CinemaHallEntity, Integer> {

    CinemaHallEntity findByIdCinemaHall(Integer id);

}
