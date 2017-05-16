package com.cinema.controller;

import com.cinema.CinemaApplication;
import com.cinema.config.BootInitializable;
import com.cinema.model.AgeRatingEntity;
import com.cinema.model.CategoryEntity;
import com.cinema.model.MovieEntity;
import com.cinema.model.SeanceEntity;
import com.cinema.services.AgeRatingRepository;
import com.cinema.services.CategoryRepository;
import com.cinema.services.MovieRepository;
import com.cinema.services.SeanceRepository;
import com.cinema.util.CustomPopupWindow;
import com.cinema.util.ImageAnalizer;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Damrod on 12.05.2017.
 */
@Component
public class ChooseSeanceController implements BootInitializable {

    private ApplicationContext springContext;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AgeRatingRepository ageRatingRepository;

    private  List<MovieEntity> movieEntityList;

    private PageController pageController;

    @FXML
    private Text selectedFilters;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    @FXML
    private TilePane mainTilePane;

    @FXML
    private Button addFilter;

    @FXML
    void addFilterClicked(ActionEvent event) {
        CustomPopupWindow filters = new CustomPopupWindow(400, 300, stackPane, vBox);
        filters.openPopupWindow();

        VBox mainBox = new VBox();
        mainBox.setPadding(new Insets(24, 24, 24, 24));

        Text popupTitle = new Text("Wybierz filtry:");
        popupTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        mainBox.getChildren().add(popupTitle);


        HBox hBox = new HBox();

        //kategorie
        Text categoryText = new Text("Kategorie:");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(24, 24, 24, 24));
        vBox.setMinWidth(120);
        vBox.setSpacing(6);
        vBox.getChildren().add(categoryText);
        List<CheckBox> checkBoxes = new ArrayList<>();
        for (CategoryEntity category : categoryRepository.findAll()) {
            CheckBox checkbox = new CheckBox(category.getName());
            checkbox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent checkboxClicked) {
                    if (checkbox.isSelected())
                        checkBoxes.add(checkbox);
                    else
                        checkBoxes.remove(checkbox);
                }
            });

            vBox.getChildren().add(checkbox);
        }
        hBox.getChildren().add(vBox);

        //min age
        Text title = new Text("Ograniczenie wiekowe:");
        VBox vBox2 = new VBox();
        vBox2.setPadding(new Insets(24, 24, 24, 24));
        vBox2.setSpacing(6);
        vBox2.getChildren().add(title);

        ToggleGroup toggleGroup = new ToggleGroup();
        for (AgeRatingEntity age : ageRatingRepository.findAll()) {
            RadioButton radioButton = new RadioButton(age.getRequiredAge().toString() + "+");
            radioButton.setToggleGroup(toggleGroup);
            vBox2.getChildren().add(radioButton);
            if(radioButton.getText().equals("18+")) {
                radioButton.setSelected(true);
            }
        }
        hBox.getChildren().add(vBox2);
        mainBox.getChildren().add(hBox);

        filters.getMainPanel().setCenter(mainBox);

        Button applyButton = new Button("Zatwierdź");
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<String> filtersList = new ArrayList<>();
                String filtersText = "";
                filtersList = getSelectedFilterList(checkBoxes, toggleGroup);
                if (!filtersList.isEmpty()) {
                    for (String filter : filtersList) {
                        filtersText += filter + ",";
                    }
                    selectedFilters.setText(filtersText.substring(0, filtersText.length() - 1));
                    filterMovieCards(getFilteredMovieList(filtersList));
                }
                filters.closePopupWindow();
            }
        });
        filters.getMainPanel().setBottom(applyButton);
        filters.getMainPanel().setAlignment(filters.getMainPanel().getBottom(), Pos.CENTER_RIGHT);
    }

    @FXML
    void btnBackClicked(MouseEvent event) {
        pageController.setPage(CinemaApplication.pageMovieInfo);

    }

    @FXML
    void btnSubmitClicked(MouseEvent event) {

    }

    @Override
    public void initConstruct() {

    }

    @Override
    public void setPageParrent(PageController parentPage) {
        pageController = parentPage;
    }

    @Override
    public void stage(Stage primaryStage) {

    }

    @Override
    public Node initView() {
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieEntityList = movieRepository.findAll();
        for (MovieEntity movie : movieEntityList) {
            List<SeanceEntity> seanceList = seanceRepository.findByMovie(movie);
            if (!seanceList.isEmpty()) {
                List<String> colorsList = new ImageAnalizer().getColors(movie);
                mainTilePane.getChildren().add(createMovieCardView(movie, seanceList, colorsList));
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }


    public HBox createMovieCardView(MovieEntity movie, List<SeanceEntity> seanceList, List<String> colors) {

        HBox card = new HBox();
        card.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        card.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        card.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        card.setStyle(
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);" +
                        "-fx-background-color: white;");

        //add image to card
        ImageView imgView = new ImageView();
        imgView.setPreserveRatio(true);
        imgView.setFitHeight(300);
        imgView.setImage(SwingFXUtils.toFXImage(getBufferedImage(movie.getCover()), null));
        card.getChildren().add(imgView);

        //add title and listview
        VBox vBox = new VBox();
        vBox.setSpacing(4.0);
        vBox.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        vBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        vBox.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        vBox.setPadding(new Insets(8, 0, 8, 12));
        vBox.setStyle("-fx-border-color:" + colors.get(0) + ";");

        //add title
        Text title = new Text(movie.getTitle());
        title.setFont(Font.font("System", FontWeight.BOLD, 16));
        title.setFill(Color.valueOf(colors.get(0)));
        title.setWrappingWidth(180);
        title.setTextAlignment(TextAlignment.CENTER);

        vBox.getChildren().add(title);

        //add seance list
        for (int i = 0; i < seanceList.size() && i < 6; i++) {
            TextFlow textFlow = new TextFlow();
            textFlow.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            textFlow.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            textFlow.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

            if (i % 2 == 0) {
                textFlow.setStyle("-fx-background-color: " + colors.get(0) + ";");
                textFlow.setOnMouseEntered(event1 -> {
                    textFlow.setStyle("-fx-background-color: " + colors.get(2) + ";");
                });
                textFlow.setOnMouseExited(event1 -> {
                    textFlow.setStyle("-fx-background-color: " + colors.get(0) + ";");
                });
            } else {
                textFlow.setStyle(" -fx-background-color: " + colors.get(1) + ";");
                textFlow.setOnMouseEntered(event1 -> {
                    textFlow.setStyle("-fx-background-color: " + colors.get(2) + ";");
                });
                textFlow.setOnMouseExited(event1 -> {
                    textFlow.setStyle("-fx-background-color: " + colors.get(1) + ";");
                });
            }

            textFlow.setTextAlignment(TextAlignment.CENTER);
            textFlow.setPadding(new Insets(4, 4, 4, 4));
            addMouseEvent(textFlow, seanceList.get(i));

            Text text = new Text(seanceList.get(i).getDate().toString());
            text.setFont(Font.font("System", FontWeight.BOLD, 14));
            text.setFill(Color.valueOf(Color.WHITE.toString()));
            textFlow.getChildren().add(text);
            vBox.getChildren().add(textFlow);
        }
        card.getChildren().add(vBox);

        card.setCacheHint(CacheHint.SPEED);

        return card;
    }

    public BufferedImage getBufferedImage(byte[] byteArray) {
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage read = null;
        try {
            read = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return read;
    }

    private void addMouseEvent(Node node, SeanceEntity seance) {
        node.setOnMouseClicked(event -> {
            ChooseSeatController chooseSeatController = springContext.getBean(ChooseSeatController.class);
            chooseSeatController.setCurrentSeance(seance);
            pageController.loadPageWithContorller(CinemaApplication.pageChooseSeat, CinemaApplication.pageChooseSeatFile, chooseSeatController);
            pageController.setPage(CinemaApplication.pageChooseSeat);
        });
    }

    private List<String> getSelectedFilterList(List<CheckBox> checkboxes, ToggleGroup toggleGroup) {
        List<String> filtersList = new ArrayList<>();
        if (!checkboxes.isEmpty()) {
            for (CheckBox cb : checkboxes) {
                if (cb.isSelected())
                    filtersList.add(cb.getText());
            }
        }
        if (toggleGroup.getSelectedToggle() != null) {
            RadioButton rb = (RadioButton) toggleGroup.getSelectedToggle();
            filtersList.add("wiek: "+rb.getText());
        }
        return filtersList;
    }

    private List<MovieEntity> getFilteredMovieList(List<String> filtersList) {


        List<CategoryEntity> categories = new ArrayList<>();
        for (String categoryName: filtersList) {
            CategoryEntity category = categoryRepository.findByName(categoryName);
            if(category != null)
                categories.add(category);
        }

        List<MovieEntity> filtredCategoryMovieList = movieRepository.findByCategoryEntities(categories);
        List<MovieEntity> filtredAgeRatingMovieList = new ArrayList<>();
        if(!filtredCategoryMovieList.isEmpty()){
            for(MovieEntity movie: filtredCategoryMovieList){
                String s1 = filtersList.get(filtersList.size()-1).replace("+", "").replace("wiek: ", "");
                int age = Integer.valueOf(s1);
                int reqAge = movie.getAgeRatingEntities().getRequiredAge();
                if(reqAge <= age){
                    filtredAgeRatingMovieList.add(movie);
                }
            }
        }

        return filtredAgeRatingMovieList;
    }

    private void filterMovieCards(List<MovieEntity> filteredMovieList) {
        mainTilePane.getChildren().clear();
        if(!filteredMovieList.isEmpty()) {
            for (MovieEntity movie : filteredMovieList) {
                List<SeanceEntity> seanceList = seanceRepository.findByMovie(movie);
                if (!seanceList.isEmpty()) {
                    List<String> colorsList = new ImageAnalizer().getColors(movie);
                    mainTilePane.getChildren().add(createMovieCardView(movie, seanceList, colorsList));
                }
            }
        }
    }


}
