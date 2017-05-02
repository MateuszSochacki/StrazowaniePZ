package com.cinema.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by msoch_000 on 02-05-2017.
 */
public class MovieHasCategoryEntityPK implements Serializable {
    private Integer movieIdMovie;
    private Integer categoryIdCategory;

    @Column(name = "MOVIE_ID_MOVIE")
    @Id
    public Integer getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(Integer movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }

    @Column(name = "CATEGORY_ID_CATEGORY")
    @Id
    public Integer getCategoryIdCategory() {
        return categoryIdCategory;
    }

    public void setCategoryIdCategory(Integer categoryIdCategory) {
        this.categoryIdCategory = categoryIdCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieHasCategoryEntityPK that = (MovieHasCategoryEntityPK) o;

        if (movieIdMovie != null ? !movieIdMovie.equals(that.movieIdMovie) : that.movieIdMovie != null) return false;
        if (categoryIdCategory != null ? !categoryIdCategory.equals(that.categoryIdCategory) : that.categoryIdCategory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = movieIdMovie != null ? movieIdMovie.hashCode() : 0;
        result = 31 * result + (categoryIdCategory != null ? categoryIdCategory.hashCode() : 0);
        return result;
    }
}
