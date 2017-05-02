package com.cinema.services;

import com.cinema.model.PriceEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by msoch_000 on 02-05-2017.
 */
public interface PriceRepository extends CrudRepository<PriceEntity, Integer> {
}
