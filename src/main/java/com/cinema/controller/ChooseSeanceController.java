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
import com.cinema.util.DateConverter;
import com.cinema.util.ImageAnalizer;
import com.cinema.util.PageController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Damrod on 12.05.2017.
 * ChooseSeanceController is a class that is binded with ChooseSeance.fxml and shows current movies with their seance and time
 */
@Component
public class ChooseSeanceController implements BootInitializable {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AgeRatingRepository ageRatingRepository;

    private ApplicationContext springContext;
    private List<MovieEntity> movieEntityList;
    private List<MovieEntity> newMovieList;
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
    private TextField searchField;

    /**
     * addFilterClicked it's a method that allows to add new filters for movie list and show movies that contain this filters
     *
     * @param event is a mouse click Event
     */
    @FXML
    void addFilterClicked(ActionEvent event) {
        CustomPopupWindow filters = new CustomPopupWindow(400, 300, stackPane, vBox);
        filters.setDoFadeTransition(false);
        filters.openPopupWindow();

        VBox mainBox = new VBox();
        mainBox.setPadding(new Insets(24, 24, 24, 24));

        Text popupTitle = new Text("Wybierz filtry:");
        popupTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        mainBox.getChildren().add(popupTitle);


        HBox hBox = new HBox();
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
        RadioButton defaultRadioButton = new RadioButton("brak");
        defaultRadioButton.setSelected(true);
        defaultRadioButton.setToggleGroup(toggleGroup);
        vBox2.getChildren().add(defaultRadioButton);
        for (AgeRatingEntity age : ageRatingRepository.findAll()) {
            RadioButton radioButton = new RadioButton(age.getRequiredAge().toString() + "+");
            radioButton.setToggleGroup(toggleGroup);
            vBox2.getChildren().add(radioButton);
        }
        hBox.getChildren().add(vBox2);
        mainBox.getChildren().add(hBox);
        filters.getMainPanel().setCenter(mainBox);
        Button applyButton = new Button("Zatwierdź");
        applyButton.setTextFill(javafx.scene.paint.Paint.valueOf("#ffff"));
        applyButton.setFont(Font.font("System", FontWeight.BOLD, 14));
        applyButton.setCursor(Cursor.HAND);
        applyButton.setStyle("-fx-background-color:  #ea4646");
        applyButton.setPadding(new Insets(8, 8, 8, 8));
        applyButton.setPrefSize(150, 36);
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<String> filtersList = new ArrayList<>();
                String filtersText = "";
                filtersList = getSelectedFilterList(checkBoxes, toggleGroup);

                for (String filter : filtersList) {
                    filtersText += filter;
                    if (!filter.equals("") && !filter.equals(filtersList.get(filtersList.size() - 1)))
                        filtersText += ", ";
                }
                selectedFilters.setText(filtersText);
                filterMovieCards(getFilteredMovieList(filtersList));
                filters.closePopupWindow();
            }
        });
        filters.getMainPanel().setBottom(applyButton);
        filters.getMainPanel().setAlignment(filters.getMainPanel().getBottom(), Pos.CENTER_RIGHT);
    }
    /**
     * btnBackClicked it's a method that allows user to go to previous controller and his view
     *
     * @param event is a mouse click Event
     */
    @FXML
    void btnBackClicked(MouseEvent event) {
        pageController.setPage(CinemaApplication.pageMovieInfo);
    }
    /**
     * btnSubmitClicked it's a secret button I would say Easter egg
     *
     * @param event is a mouse click Event
     */
    @FXML
    void btnSubmitClicked(MouseEvent event) {
    }

    @Override
    public void setPageParrent(PageController parentPage) {
        pageController = parentPage;
    }


    /**
     * seatchSeanseByName it's a method that allows user to look for movies that contain certain String
     *
     * @param title is a String which can be modified by user
     */
    private void searchSeanceByName(String title) {

        List<MovieEntity> movies = new ArrayList<>();
        if (!newMovieList.isEmpty()) {
            for (MovieEntity movie : newMovieList) {
                if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    movies.add(movie);
                }
            }
            selectedFilters.setText(searchField.getText());
            filterMovieCards(movies);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieEntityList = movieRepository.findAll();
        newMovieList = new ArrayList<>();
        for (MovieEntity movie : movieEntityList) {
            List<SeanceEntity> seanceList = seanceRepository.findByMovieOrderByDate(movie);
            if (!seanceList.isEmpty()) {
                List<String> colorsList = new ImageAnalizer().getColors(movie);
                Node card = createMovieCardView(movie, seanceList, colorsList);
                if (card != null) {
                    mainTilePane.getChildren().add(card);
                    newMovieList.add(movie);
                }
            }
        }

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> values, String oldValue, String newValue) {
                if (newValue != null) {
                    ChooseSeanceController.this.searchSeanceByName(newValue);
                }
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    /**
     * createMovieCardView it's a method that creates movie cards depending on their params
     * @param movie is a specified movie
     * @param seanceList is a list with all movies we downloaded from database
     * @param colors is a color that is dynamically created depending on cover colors
     * @return returns cover, seances and theirs time which is displayed on view
     */
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

        Date date = new Date();
        int seanceCount = 0;
        //add seance list
        for (int i = 0; i < seanceList.size() && i < 6; i++) {
            if (seanceList.get(i).getDate().getTime() > date.getTime()) {
                seanceCount++;
                TextFlow textFlow = new TextFlow();
                textFlow.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                textFlow.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                textFlow.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                textFlow.setCursor(Cursor.HAND);

                if (i % 2 == 0) {
                    textFlow.setStyle("-fx-background-color: " + colors.get(0) + ";");
                    textFlow.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event1) {
                            textFlow.setStyle("-fx-background-color: " + colors.get(2) + ";");
                        }
                    });
                    textFlow.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event1) {
                            textFlow.setStyle("-fx-background-color: " + colors.get(0) + ";");
                        }
                    });
                } else {
                    textFlow.setStyle(" -fx-background-color: " + colors.get(1) + ";");
                    textFlow.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event1) {
                            textFlow.setStyle("-fx-background-color: " + colors.get(2) + ";");
                        }
                    });
                    textFlow.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event1) {
                            textFlow.setStyle("-fx-background-color: " + colors.get(1) + ";");
                        }
                    });
                }

                textFlow.setTextAlignment(TextAlignment.CENTER);
                textFlow.setPadding(new Insets(4, 4, 4, 4));
                addMouseEvent(textFlow, seanceList.get(i));

                DateConverter conv = new DateConverter();

                Text text = new Text(conv.convertDataToString(seanceList.get(i).getDate()));
                text.setFont(Font.font("System", FontWeight.BOLD, 14));
                text.setFill(Color.valueOf(Color.WHITE.toString()));
                textFlow.getChildren().add(text);
                vBox.getChildren().add(textFlow);
            }
        }
        if (seanceCount == 0) {
            return null;
        }
        card.getChildren().add(vBox);
        card.setCacheHint(CacheHint.SPEED);

        return card;
    }
    /**
     * getBufferedImage it's a method tries to change byteArray to actual image
     * @param byteArray is a string that contains image
     * @return returns actual image
     */
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
    /**
     * addMouseEvent it's a method to allow users go to certain seance
     * @param node is a node that user actually click
     * @param seance is a seance connected with node
     *
     */
    private void addMouseEvent(Node node, SeanceEntity seance) {
        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ChooseSeatController chooseSeatController = springContext.getBean(ChooseSeatController.class);
                chooseSeatController.setCurrentSeance(seance);
                pageController.loadPageWithContorller(CinemaApplication.pageChooseSeat, CinemaApplication.pageChooseSeatFile, chooseSeatController);
                pageController.setPage(CinemaApplication.pageChooseSeat);
            }
        });
    }
    /**
     * getSelectedFilterList it's a method that
     * @param checkboxes is a list of all checkboxes that are connected to all categories
     * @param toggleGroup is a group where all radio buttons are in
     * @return returns list with all selected filters by user
     */
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
            if (rb.getText().equals("brak")) {
                filtersList.add("");
            } else {
                filtersList.add("wiek: " + rb.getText());
            }
        }
        return filtersList;
    }
    /**
     * getSelectedFilterList it's a method that shows all movies with compatible filters
     * @param filtersList is a list of all filters that user checked
     * @return returns list with all movies that are compatible with filters
     */
    private List<MovieEntity> getFilteredMovieList(List<String> filtersList) {
        List<MovieEntity> filtredMovieList = new ArrayList<>();
        filtredMovieList = newMovieList;
        if (!filtersList.get(0).equals("")) {
            List<CategoryEntity> categories = new ArrayList<>();
            for (String categoryName : filtersList) {
                CategoryEntity category = categoryRepository.findByName(categoryName);
                if (category != null)
                    categories.add(category);
            }
            if (categories.isEmpty()) {
                categories = categoryRepository.findAll();
            }

            List<MovieEntity> filtredCategoryMovieList = new ArrayList<>();
            for (MovieEntity movie : filtredMovieList) {
                int searchProgress = 0;
                for (int i = 0; i < movie.getCategoryEntities().size(); i++) {
                    for (int j = 0; j < categories.size(); j++) {
                        CategoryEntity currentCategory = movie.getCategoryEntities().get(i);
                        if (currentCategory.getName().equals(categories.get(j).getName())) {
                            searchProgress++;
                            break;
                        }
                    }
                }
                if (searchProgress > 0) {
                    filtredCategoryMovieList.add(movie);
                }
            }
            if (!filtersList.get(filtersList.size() - 1).equals("")) {
                List<MovieEntity> filtredAgeRatingMovieList = new ArrayList<>();
                for (MovieEntity movie : filtredCategoryMovieList) {
                    int ageReqied = movie.getAgeRatingEntities().getRequiredAge();
                    int ageSelected = Integer.valueOf(filtersList.get(filtersList.size() - 1).replace("+", "").replace("wiek: ", ""));
                    if (ageReqied <= ageSelected) {
                        filtredAgeRatingMovieList.add(movie);
                    }
                }
                return filtredAgeRatingMovieList;
            }
            return filtredCategoryMovieList;
        }
        return filtredMovieList;
    }
    /**
     * getSelectedFilterList it's a method that clears main view to shows movies that are currently in demand by user
     * @param filteredMovieList is a list of all movies that are compatible with filters
     *
     *
     */
    private void filterMovieCards(List<MovieEntity> filteredMovieList) {
        mainTilePane.getChildren().clear();
        if (!filteredMovieList.isEmpty()) {
            for (MovieEntity movie : filteredMovieList) {
                List<SeanceEntity> seanceList = seanceRepository.findByMovieOrderByDate(movie);
                if (!seanceList.isEmpty()) {
                    List<String> colorsList = new ImageAnalizer().getColors(movie);
                    mainTilePane.getChildren().add(createMovieCardView(movie, seanceList, colorsList));
                }
            }
        }
    }
}
