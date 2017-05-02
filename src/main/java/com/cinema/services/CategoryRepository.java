package com.cinema.services;

import com.cinema.model.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Dominik on 14.04.2017.
 */


public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAll();

}
