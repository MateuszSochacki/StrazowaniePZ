package com.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by msoch_000 on 09-04-2017.
 */
@Entity
@Table(name = "MOVIE", schema = "sql11167212")

public class MovieEntity {
    private int idMovie;
    private String title;
    private Integer duration;
    private String director;
    private Date releaseDate;
    private String description;

    @Id
    @Column(name = "id_movie")
    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "duration")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "director")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Basic
    @Column(name = "release_date")
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieEntity that = (MovieEntity) o;

        if (idMovie != that.idMovie) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (director != null ? !director.equals(that.director) : that.director != null) return false;
        if (releaseDate != null ? !releaseDate.equals(that.releaseDate) : that.releaseDate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMovie;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
