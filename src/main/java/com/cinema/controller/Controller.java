package com.cinema.controller;

import com.cinema.dao.PriceEntityDao;
import com.cinema.model.PriceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by msoch_000 on 03-04-2017.
 */


@Component
public class Controller {


    private PriceEntityDao priceEntityDao;

    @Autowired
    public Controller(PriceEntityDao priceEntityDao) {
        this.priceEntityDao = priceEntityDao;

    }

    public void test() {


        PriceEntity priceEntityEntity = new PriceEntity();
        priceEntityEntity.setName("Fantasy");
        priceEntityEntity.setIdPrice(1);
        priceEntityEntity.setValue(25.0);
        priceEntityDao.save(priceEntityEntity);

    }
}

