package com.cinema.controller;

import com.cinema.config.BootInitializable;
import com.cinema.model.CategoryEntity;
import com.cinema.model.MovieEntity;
import com.cinema.util.ImageAnalizer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Damrod on 02.06.2017.
 * this class creates back of a card for MovieInfo. It shows movie info, description, category, trailer ect.
 */
public class MovieInfoCardController implements Initializable {

    private MovieEntity movie;
    private Image image;
    private List<String> colors;

    /**
     * @param movie object of MovieEntity needed to get data for card.
     * @param image image used as background of the card
     * @param colors collor pallete used to paint movie card.
     */
    public MovieInfoCardController(MovieEntity movie, Image image, List<String> colors) {
        this.movie = movie;
        this.image = image;
        this.colors = colors;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set data
        cardMovieImage.setImage(image);
        cardMovieTitle.setText(movie.getTitle());
        cardMovieDirector.setText(movie.getDirector());
        cardMovieTime.setText(movie.getDuration().toString());
        cardMovieDate.setText(movie.getReleaseDate().toString());
        cardMovieAge.setText("od " + movie.getAgeRatingEntities().getRequiredAge().toString() + " lat");

        List<CategoryEntity> list = movie.getCategoryEntities();
        String categoriesString = "";
        for (CategoryEntity category : list) {
            categoriesString += category.getName() + ", ";
        }
        categoriesString.substring(0, categoriesString.length() - 1);

        cardMovieCategories.setText(categoriesString);
        cardMovieDescription.setText(movie.getDescription());

        //set styles
        cardMovieTitle.getParent().setStyle("-fx-background-color: " + colors.get(0) + ";");
        cardMovieTrailer.setStyle("-fx-background-color: #000000;");
    }


    @FXML
    private ImageView cardMovieImage;

    @FXML
    private Text cardMovieTitle;

    @FXML
    private Text cardMovieDirector;

    @FXML
    private Text cardMovieTime;

    @FXML
    private Text cardMovieCategories;

    @FXML
    private Text cardMovieDate;

    @FXML
    private Text cardMovieAge;

    @FXML
    private Text cardMovieDescription;

    @FXML
    private WebView cardMovieTrailer;

    public WebView getCardMovieTrailer() {
        return cardMovieTrailer;
    }

    public void setCardMovieTrailer(WebView cardMovieTrailer) {
        this.cardMovieTrailer = cardMovieTrailer;
    }
}
