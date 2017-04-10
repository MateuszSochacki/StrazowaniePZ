package com.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 10-04-2017.
 */
@Entity
@Table(name = "AGE_RATING", schema = "PUBLIC", catalog = "DOCUMENTS")
public class AgeRatingEntity {
    private int idAgeRating;
    private Integer requiredAge;

    @Id
    @Column(name = "ID_AGE_RATING")
    public int getIdAgeRating() {
        return idAgeRating;
    }

    public void setIdAgeRating(int idAgeRating) {
        this.idAgeRating = idAgeRating;
    }

    @Basic
    @Column(name = "REQUIRED_AGE")
    public Integer getRequiredAge() {
        return requiredAge;
    }

    public void setRequiredAge(Integer requiredAge) {
        this.requiredAge = requiredAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgeRatingEntity that = (AgeRatingEntity) o;

        if (idAgeRating != that.idAgeRating) return false;
        if (requiredAge != null ? !requiredAge.equals(that.requiredAge) : that.requiredAge != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAgeRating;
        result = 31 * result + (requiredAge != null ? requiredAge.hashCode() : 0);
        return result;
    }
}
