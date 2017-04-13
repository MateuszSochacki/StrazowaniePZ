package com.cinema.dao;

import controller.Controller;
import model.CategoryEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by msoch_000 on 13-04-2017.
 */

@Configuration
public class Config {


    @Bean
    public Controller getController() {

        return new Controller();
    }


}
