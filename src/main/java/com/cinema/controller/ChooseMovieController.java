package com.cinema.controller;

import com.cinema.config.BootInitializable;
import com.cinema.model.AgeRatingEntity;
import com.cinema.model.CategoryEntity;
import com.cinema.model.MovieEntity;
import com.cinema.services.AgeRatingRepository;
import com.cinema.services.CategoryRepository;
import com.cinema.services.MovieRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by xareen on 04.05.2017.
 */
@Component
public class ChooseMovieController implements BootInitializable {

    @FXML
    private GridPane gridPaneMovie;
    @FXML
    private ComboBox<Integer> comboBoxAge;
    @FXML
    private ComboBox<String> comboBoxDuration;
    @FXML
    private ComboBox<String> comboBoxGenre;
    @FXML
    private Label labelGenre;
    @FXML
    private Label labelDuration;
    @FXML
    private Label labelAge;
    @FXML
    private TextArea textAreaDescription;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AgeRatingRepository ageRatingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private ApplicationContext springContext;

    public void setFields() {
        List<Integer> ageRatingList = new ArrayList<>();
        for (AgeRatingEntity i:ageRatingRepository.findAll())
        {
            ageRatingList.add(i.getRequiredAge());
        }
        ObservableList<Integer> ageRatingEntityObservableList =
                FXCollections.observableArrayList(ageRatingList);
        comboBoxAge.setItems(ageRatingEntityObservableList);

        List<String> genreList = new ArrayList<>();
        for (CategoryEntity i:categoryRepository.findAll())
        {
            genreList.add(i.getName());
        }
        ObservableList<String> categoryEntityObservableList =
                FXCollections.observableArrayList(genreList);
        comboBoxGenre.setItems(categoryEntityObservableList);

        comboBoxDuration.getItems().addAll(">1h","1h<","2h<");
        ImageView imgView = new ImageView();

        gridPaneMovie.add(imgView,0,0);













    }
    @Override
    public void initConstruct() {
    }

    @Override
    public void stage(Stage primaryStage) {

    }

    @Override
    public Node initView() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/scene/chooseMovieController/Main.fxml"));
            loader.setController(springContext.getBean(this.getClass()));

            return loader.load();
        } catch (IOException e) {
            System.err.println("Can't load scene");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        setFields();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    public void onAgeChanged(ActionEvent actionEvent){

    }
}
