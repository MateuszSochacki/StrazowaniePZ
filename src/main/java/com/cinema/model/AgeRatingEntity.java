package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
public class AgeRatingEntity {
    private Integer idAgeRating;
    private Integer requiredAge;

    @Id
    @GeneratedValue
    public Integer getIdAgeRating() {
        return idAgeRating;
    }

    public void setIdAgeRating(Integer idAgeRating) {
        this.idAgeRating = idAgeRating;
    }


    public Integer getRequiredAge() {
        return requiredAge;
    }

    public void setRequiredAge(Integer requiredAge) {
        this.requiredAge = requiredAge;
    }

}
