package com.cinema.model;

import javax.persistence.*;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
@Table(name = "MOVIE_HAS_CATEGORY", schema = "PUBLIC", catalog = "DATABASE")
@IdClass(MovieHasCategoryEntityPK.class)
public class MovieHasCategoryEntity {
    private Integer movieIdMovie;
    private Integer categoryIdCategory;

    @Id
    @Column(name = "MOVIE_ID_MOVIE")
    public Integer getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(Integer movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }

    @Id
    @Column(name = "CATEGORY_ID_CATEGORY")
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

        MovieHasCategoryEntity that = (MovieHasCategoryEntity) o;

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
