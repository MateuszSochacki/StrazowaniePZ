package com.cinema.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by msoch_000 on 02-05-2017.
 */
@Entity
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_movie")
    private Integer idMovie;

    private String title;
    private Integer duration;
    private String director;
    private Date releaseDate;
    private String description;



    //fetch musi być ustawione na eager, dzięki czemu pobiera też inne encje które wykorzystuje, w tym przypadku CategoryEntity
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_category", joinColumns = {@JoinColumn(name = "id_movie")}, inverseJoinColumns = {@JoinColumn(columnDefinition = "id_category")})
    private List<CategoryEntity> categoryEntities;

    //TODO: analogicznie do ageRating

    public Integer getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryEntity> getCategoryEntities() {
        return categoryEntities;
    }

    public void setAgeRatingEntity(List<CategoryEntity> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }


}
