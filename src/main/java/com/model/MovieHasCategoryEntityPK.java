package com.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by msoch_000 on 06-04-2017.
 */
public class MovieHasCategoryEntityPK implements Serializable {
    private int movieIdMovie;
    private int categoryIdCategory;

    @Column(name = "MOVIE_id_movie")
    @Id
    public int getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(int movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }

    @Column(name = "CATEGORY_id_category")
    @Id
    public int getCategoryIdCategory() {
        return categoryIdCategory;
    }

    public void setCategoryIdCategory(int categoryIdCategory) {
        this.categoryIdCategory = categoryIdCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieHasCategoryEntityPK that = (MovieHasCategoryEntityPK) o;

        if (movieIdMovie != that.movieIdMovie) return false;
        if (categoryIdCategory != that.categoryIdCategory) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = movieIdMovie;
        result = 31 * result + categoryIdCategory;
        return result;
    }
}
