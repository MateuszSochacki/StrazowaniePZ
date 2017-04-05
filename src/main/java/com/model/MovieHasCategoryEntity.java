package com.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 06-04-2017.
 */
@Entity
@Table(name = "MOVIE_has_CATEGORY", schema = "sql11167212")
@IdClass(MovieHasCategoryEntityPK.class)
public class MovieHasCategoryEntity {
    private int movieIdMovie;
    private int categoryIdCategory;

    @Id
    @Column(name = "MOVIE_id_movie")
    public int getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(int movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }

    @Id
    @Column(name = "CATEGORY_id_category")
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

        MovieHasCategoryEntity that = (MovieHasCategoryEntity) o;

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
