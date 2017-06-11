package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 * A class responsible for movie genre filter like "thriller", "sci-fi" etc.
 */
@Entity
public class CategoryEntity {

    @Id
    @GeneratedValue
    @Column(name = "id_category")
    private Integer idCategory;

    private String name;

    public Integer getIdCategory() {
        return idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
