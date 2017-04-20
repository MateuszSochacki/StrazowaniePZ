package com.cinema.dao;

import com.cinema.model.PriceEntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by msoch_000 on 13-04-2017.
 */

@Repository
@javax.transaction.Transactional
public interface PriceEntityDao extends JpaRepository<PriceEntity, Integer> {

    @Override
    <S extends PriceEntity> S save(S s);
}
