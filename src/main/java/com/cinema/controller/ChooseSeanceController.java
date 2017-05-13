package com.cinema.controller;

import com.cinema.CinemaApplication;
import com.cinema.config.BootInitializable;
import com.cinema.model.MovieEntity;
import com.cinema.model.SeanceEntity;
import com.cinema.services.MovieRepository;
import com.cinema.services.SeanceRepository;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
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

/**
 * Created by Damrod on 12.05.2017.
 */
@Component
public class ChooseSeanceController implements BootInitializable {

    private ApplicationContext springContext;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    SeanceRepository seanceRepository;

    private PageController pageController;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private TilePane mainTilePane;

    @FXML
    void btnBackClicked(MouseEvent event) {
        List <SeanceEntity> seanceList = new ArrayList<>();

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
                mainTilePane.getChildren().add(createMovieCardView(movie, seanceList));
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }


    public HBox createMovieCardView(MovieEntity movie, List<SeanceEntity> seanceList){

        String primaryColor ="#7591b5";
        String secondaryColor ="#84b5d0";

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
        vBox.setStyle("-fx-border-color:"+secondaryColor+";");

        //add title
        Text title = new Text(movie.getTitle());
        title.setFont(Font.font ("System", FontWeight.BOLD, 16 ));
        title.setFill(Color.valueOf(primaryColor));
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
                textFlow.setStyle("-fx-background-color: "+primaryColor+";");
            else
                textFlow.setStyle("-fx-background-color: "+secondaryColor+";");
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

    private void addMouseEvent(Node node, SeanceEntity seance){
        node.setOnMouseClicked(event -> {
            ChooseSeatController chooseSeatController = springContext.getBean(ChooseSeatController.class);
            chooseSeatController.setCurrentSeance(seance);
            pageController.loadPageWithContorller(CinemaApplication.pageChooseSeat, CinemaApplication.pageChooseSeatFile, chooseSeatController);
            pageController.setPage(CinemaApplication.pageChooseSeat);
        });
    }
}
