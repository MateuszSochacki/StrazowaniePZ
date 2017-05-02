package com.cinema.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
@Table(name = "MOVIE", schema = "PUBLIC", catalog = "DATABASE")
public class MovieEntity {
    private Integer idMovie;
    private String title;
    private Integer duration;
    private String director;
    private Date releaseDate;
    private String description;
    private Integer ageRatingIdAgeRating;

    @Id
    @Column(name = "ID_MOVIE")
    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "DURATION")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "DIRECTOR")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Basic
    @Column(name = "RELEASE_DATE")
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "AGE_RATING_ID_AGE_RATING")
    public Integer getAgeRatingIdAgeRating() {
        return ageRatingIdAgeRating;
    }

    public void setAgeRatingIdAgeRating(Integer ageRatingIdAgeRating) {
        this.ageRatingIdAgeRating = ageRatingIdAgeRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieEntity that = (MovieEntity) o;

        if (idMovie != null ? !idMovie.equals(that.idMovie) : that.idMovie != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (director != null ? !director.equals(that.director) : that.director != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (ageRatingIdAgeRating != null ? !ageRatingIdAgeRating.equals(that.ageRatingIdAgeRating) : that.ageRatingIdAgeRating != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMovie != null ? idMovie.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (ageRatingIdAgeRating != null ? ageRatingIdAgeRating.hashCode() : 0);
        return result;
    }
}
