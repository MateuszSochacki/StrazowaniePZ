package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
@Table(name = "AGE_RATING", schema = "PUBLIC", catalog = "DATABASE")
public class AgeRatingEntity {
    private Integer idAgeRating;
    private Integer requiredAge;

    @Id
    @GeneratedValue
    @Column(name = "ID_AGE_RATING", nullable = false)
    public Integer getIdAgeRating() {
        return idAgeRating;
    }

    public void setIdAgeRating(Integer idAgeRating) {
        this.idAgeRating = idAgeRating;
    }

    @Basic
    @Column(name = "REQUIRED_AGE", nullable = true)
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

        if (idAgeRating != null ? !idAgeRating.equals(that.idAgeRating) : that.idAgeRating != null) return false;
        if (requiredAge != null ? !requiredAge.equals(that.requiredAge) : that.requiredAge != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAgeRating != null ? idAgeRating.hashCode() : 0;
        result = 31 * result + (requiredAge != null ? requiredAge.hashCode() : 0);
        return result;
    }
}
