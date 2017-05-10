package com.cinema.controller;

import com.cinema.config.BootInitializable;
import com.cinema.model.AgeRatingEntity;
import com.cinema.model.CategoryEntity;
import com.cinema.model.MovieEntity;
import com.cinema.model.MovieHasCategoryEntity;
import com.cinema.services.AgeRatingRepository;
import com.cinema.services.CategoryRepository;
import com.cinema.services.MovieHasCategoryRepository;
import com.cinema.services.MovieRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by xareen on 04.05.2017.
 */
@Component
public class ChooseMovieController implements BootInitializable {

    @FXML
    private ScrollPane scrollPanelMovie;
    @FXML
    private ComboBox<Integer> comboBoxAge;
    @FXML
    private ComboBox<Integer> comboBoxDuration;
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
    GridPane gridPaneMovie = new GridPane();

    List<MovieEntity> movieList;


    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private AgeRatingRepository ageRatingRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MovieHasCategoryRepository movieHasCategoryRepository;

    private ApplicationContext springContext;
    public File f = new File("../resources/css/style.css");


    public void setDataGrid(int listSize)
    {
        //gridPaneMovie.setGridLinesVisible(true);
        scrollPanelMovie.setContent(gridPaneMovie);
        RowConstraints templateRow = new RowConstraints();
        templateRow.setPrefHeight(250);
        gridPaneMovie.getRowConstraints().add(templateRow);
        gridPaneMovie.setAlignment(Pos.CENTER);
        gridPaneMovie.getStylesheets().clear();
        gridPaneMovie.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));



        for (int i =0;i<4;i++)
        {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(25); //180 kazda z nich
            column.setHalignment(HPos.CENTER);
            gridPaneMovie.getColumnConstraints().add(column);
        }

        for (int i=0;i<listSize%3;i++)
        {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(250);
            row.setValignment(VPos.CENTER);
            gridPaneMovie.getRowConstraints().add(row);

        }

    }
    public void fillDataGrid(List<MovieEntity> movieEntityList)
    {
        int col = 0;
        int r =0;
        for (MovieEntity i:movieEntityList)
        {
            StackPane panel = new StackPane();
            panel.setPadding(new Insets(10,20,20,20));
            panel.getStyleClass().add("stackPane");
            ImageView imgView = new ImageView();
            imgView.setFitWidth(140);
            imgView.setFitHeight(190);
            byte[] byteArray = i.getCover();
            ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
            BufferedImage read = null;
            try {
                read = ImageIO.read(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgView.setImage(SwingFXUtils.toFXImage(read, null));
            panel.setAlignment(imgView,Pos.TOP_CENTER);
            Label label = new Label();
            label.setText(i.getTitle());
            label.getStyleClass().add("stackPaneLabel");
            //imgView.setImage(i.getCover());
            panel.getChildren().add(imgView);
            panel.setAlignment(label,Pos.BOTTOM_CENTER);
            panel.getChildren().add(label);
            //movieList.add(i.getTitle());
            //label.setText(i.getTitle());
            //gridPaneMovie.add(label,col,r);
            if (col ==4){
                col=0;
                r++;
            }
            gridPaneMovie.add(panel,col,r);
            col++;

        }
    }
    public void fillComboBox()
    {
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

        comboBoxDuration.getItems().addAll(30,60,120,180);
    }

    public void filterDataGridByGenre()
    {


        comboBoxGenre.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
//                System.out.println(ov); Object
//                System.out.println(t); Poprzednio wybrany
//                System.out.println(t1); Nowo wybrany
                CategoryEntity category = categoryRepository.findAll().stream().filter(categoryEntity -> categoryEntity.getName().equals(t1)).findAny().get();
                List<MovieHasCategoryEntity> movieListAfterFilterById = movieHasCategoryRepository.findAll().stream().filter(movieHasCategoryEntity -> movieHasCategoryEntity.getCategoryIdCategory().equals(category.getIdCategory())).collect(Collectors.toList());
                List<MovieEntity> movieListAfterFilter= new ArrayList<>();
                for (MovieHasCategoryEntity i :movieListAfterFilterById) {
                    movieListAfterFilter.addAll(movieRepository.findAll().stream().filter(movieEntity -> movieEntity.getIdMovie().equals(i.getMovieIdMovie())).collect(Collectors.toList()));
                }
                gridPaneMovie.getChildren().clear();
                movieList.clear();
                movieList.addAll(movieListAfterFilter);
                setDataGrid(movieList.size());
                fillDataGrid(movieList);
            }
        });
    }

    public void filterDataGridByAge()
    {

        comboBoxAge.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {

                AgeRatingEntity ageRatingEntity = ageRatingRepository.findAll().stream().filter(p -> p.getRequiredAge().equals(newValue)).findAny().get();
                List<MovieEntity> movieListAfterFilterById = movieRepository.findAll().stream().filter(p -> p.getAgeRatingIdAgeRating().equals(ageRatingEntity.getIdAgeRating())).collect(Collectors.toList());
                gridPaneMovie.getChildren().clear();
                setDataGrid(movieListAfterFilterById.size());
                fillDataGrid(movieListAfterFilterById);
            }

        });

    }
    public void filterDataGridByDuration()
    {
        comboBoxDuration.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override public void changed(ObservableValue ov, Integer t, Integer t1) {

                List<MovieEntity> movieListAfterFilter =new ArrayList<>();
                movieListAfterFilter=movieRepository.findAll().stream().filter(p->p.getDuration()>=t1).collect(Collectors.toList());
                gridPaneMovie.getChildren().clear();
                movieList.clear();
                movieList.addAll(movieListAfterFilter);
                setDataGrid(movieList.size());
                fillDataGrid(movieList);
            }
        });
    }
    public void resetFilter()
    {
        movieList = movieRepository.findAll();
        comboBoxAge.getSelectionModel().clearSelection();
        comboBoxGenre.getSelectionModel().clearSelection();
        comboBoxDuration.getSelectionModel().clearSelection();
        gridPaneMovie.getChildren().clear();
        setDataGrid(movieList.size());
        fillDataGrid(movieList);
    }

    public void setFields() {


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
        movieList = movieRepository.findAll();
        setFields();
        setDataGrid(movieList.size());
        fillDataGrid(movieList);
        fillComboBox();
        filterDataGridByGenre();
        filterDataGridByAge();
        filterDataGridByDuration();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    public void onAgeChanged(ActionEvent actionEvent){

    }
    @FXML
    private void genreChange(ActionEvent event)
    {
    }
}
