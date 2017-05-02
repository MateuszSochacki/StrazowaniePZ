package com.cinema.services;

import com.cinema.model.AgeRatingEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by msoch_000 on 02-05-2017.
 */
public interface AgeRatingRepository extends CrudRepository<AgeRatingEntity, Integer> {
}
