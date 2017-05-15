package com.cinema.controller;

import com.cinema.CinemaApplication;
import com.cinema.config.BootInitializable;
import com.cinema.model.MovieEntity;
import com.cinema.model.SeanceEntity;
import com.cinema.services.AgeRatingRepository;
import com.cinema.services.CategoryRepository;
import com.cinema.services.MovieRepository;
import com.cinema.services.SeanceRepository;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
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

    private PageController pageController;

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

        BorderPane filterPanel = new BorderPane();
        filterPanel.setMaxSize(450, 450);
        filterPanel.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        filterPanel.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        filterPanel.setStyle("-fx-background-color: #ecedff;" +
                "-fx-border-color: #333333;"+
        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");


        Hyperlink textClose = new Hyperlink("X");
        textClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closePopupWindow(stackPane, filterPanel, vBox);
            }
        });

        filterPanel.setTop(textClose);
        filterPanel.setAlignment(filterPanel.getTop(), Pos.CENTER_RIGHT);


        Button applyButton = new Button("Zatwierdź");
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closePopupWindow(stackPane, filterPanel, vBox);

            }
        });

        filterPanel.setBottom(applyButton);
        filterPanel.setAlignment(filterPanel.getBottom(), Pos.CENTER_RIGHT);
        filterPanel.setPadding(new Insets(12, 12, 12, 12));
        openPopupWindow(stackPane, filterPanel, vBox);


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
        List<MovieEntity> movieEntityList = movieRepository.findAll();
        for (MovieEntity movie: movieEntityList) {
            List<SeanceEntity> seanceList = seanceRepository.findByMovie(movie);
            if(!seanceList.isEmpty())
            {
                List <String> colorsList  = new ImageAnalizer().getColors(movie);
                mainTilePane.getChildren().add(createMovieCardView(movie, seanceList, colorsList));
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }


    public HBox createMovieCardView(MovieEntity movie, List<SeanceEntity> seanceList, List<String > colors){

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
        vBox.setPadding(new Insets (8, 0, 8, 12));
        vBox.setStyle("-fx-border-color:"+colors.get(0)+";");

        //add title
        Text title = new Text(movie.getTitle());
        title.setFont(Font.font ("System", FontWeight.BOLD, 16 ));
        title.setFill(Color.valueOf(colors.get(0)));
        title.setWrappingWidth(180);
        title.setTextAlignment(TextAlignment.CENTER);

        vBox.getChildren().add(title);

        //add seance list
        for(int i=0; i<seanceList.size() && i<6; i++){
            TextFlow textFlow = new TextFlow();
            textFlow.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            textFlow.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            textFlow.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

            if(i%2==0)
            {
                textFlow.setStyle("-fx-background-color: "+colors.get(0)+";");
                textFlow.setOnMouseEntered(event1 -> {textFlow.setStyle("-fx-background-color: "+colors.get(2)+";");});
                textFlow.setOnMouseExited(event1 -> {textFlow.setStyle("-fx-background-color: "+colors.get(0)+";");});
            }
            else {
                textFlow.setStyle(" -fx-background-color: " + colors.get(1) + ";");
                textFlow.setOnMouseEntered(event1 -> {textFlow.setStyle("-fx-background-color: " + colors.get(2) + ";");});
                textFlow.setOnMouseExited(event1 -> {textFlow.setStyle("-fx-background-color: " + colors.get(1) + ";");});
            }

            textFlow.setTextAlignment(TextAlignment.CENTER);
            textFlow.setPadding(new Insets (4, 4, 4, 4));
            addMouseEvent(textFlow, seanceList.get(i));

            Text text = new Text(seanceList.get(i).getDate().toString());
            text.setFont(Font.font ("System", FontWeight.BOLD, 14 ));
            text.setFill(Color.valueOf(Color.WHITE.toString()));
            textFlow.getChildren().add(text);
            vBox.getChildren().add(textFlow);
        }
        card.getChildren().add(vBox);

        card.setCacheHint(CacheHint.SPEED);

        return card;
    }

    public BufferedImage getBufferedImage( byte[] byteArray){
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

    public void openPopupWindow(StackPane parent, Node popup, Node targetBlur) {
        targetBlur.setDisable(true);

        GaussianBlur blur = new GaussianBlur(0);
        targetBlur.setEffect(blur);

        DoubleProperty valueBlurRadius = new SimpleDoubleProperty(0);
        valueBlurRadius.addListener((observable, oldV, newV)->
        {
            blur.setRadius(newV.doubleValue());
        });
        Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(valueBlurRadius, 30);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        FadeTransition ft = new FadeTransition();
        ft.setNode(popup);
        ft.setDuration(new Duration(500));
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        parent.getChildren().add(popup);
        ft.play();
    }
    public void closePopupWindow(StackPane parent, Node popup, Node targetBlur) {

        GaussianBlur blur = new GaussianBlur(30);
        targetBlur.setEffect(blur);

        DoubleProperty valueBlurRadius = new SimpleDoubleProperty(30);
        valueBlurRadius.addListener((observable, oldV, newV)->
        {
            blur.setRadius(newV.doubleValue());
        });
        Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(valueBlurRadius, 0);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        final DoubleProperty opacity = popup.opacityProperty();
        Timeline fade = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                new KeyFrame(new Duration(500), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        parent.getChildren().remove(popup);
                        targetBlur.setDisable(false);
                    }
                },new KeyValue(opacity, 0.0)));
        fade.play();
    }

    private List<MovieEntity> getFilteredMovieList() {
        //TO DO
        return null;
    }



}
