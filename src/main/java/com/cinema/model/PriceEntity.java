package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPrice;

    public Integer getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(Integer idPrice) {
        this.idPrice = idPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    private String name;
    private Double value;


}
