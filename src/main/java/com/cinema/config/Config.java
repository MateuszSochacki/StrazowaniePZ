package com.cinema.config;
import com.cinema.controller.Controller;
import com.cinema.dao.PriceEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    public void setPriceEntityDao(PriceEntityDao priceEntityDao) {
        this.priceEntityDao = priceEntityDao;
    }


    private PriceEntityDao priceEntityDao;

    @Bean
    public Controller getController() {

        return new Controller(priceEntityDao);
    }
    @Bean
    public PriceEntityDao getPriceEntityDao() {


        return priceEntityDao;
    }


}
