package com.cinema.services;

import com.cinema.model.CategoryEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dominik on 14.04.2017.
 */


public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    List<CategoryEntity> findAll();

}
