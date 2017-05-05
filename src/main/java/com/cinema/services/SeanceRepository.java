package com.cinema.services;


import com.cinema.model.CinemaHallEntity;
import com.cinema.model.MovieEntity;
import com.cinema.model.SeanceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by msoch_000 on 02-05-2017.
 */
public interface SeanceRepository extends CrudRepository<SeanceEntity, Integer> {

    List<SeanceEntity> findAll();
    List<SeanceEntity> findByMovie(MovieEntity movieEntity);
    SeanceEntity findByMovieIdMovie(Integer idMovie);

}
