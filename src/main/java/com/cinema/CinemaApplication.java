package com.cinema;

import com.cinema.controller.*;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class CinemaApplication extends Application {


    private static String[] argument;
    private ApplicationContext springContext = null;

    //Tutaj dodać wszystkie pliki FXML oraz nadać im unikalne ID
    public static final String pageChooseSeat = "pageChooseSeat";
    public static final String pageChooseSeatFile = "scene/ChooseSeat.fxml";

    public static final String pageSummary = "pageSummary";
    public static final String pageSummaryFile = "scene/Summary.fxml";

    public static final String pageChooseMovie = "pageChooseMovie";
    public static final String pageChooseMovieFile = "scene/ChooseMovie.fxml";

    public static final String pageChooseSeance = "pageChooseSeance";
    public static final String pageChooseSeanceFile = "scene/ChooseSeance.fxml";

    public static final String pageMovieInfo = "pageMovieInfo";
    public static final String pageMovieInfoFile = "Scene/MovieInfo.fxml";


    @Override
    public void start(Stage primaryStage) throws Exception {
        Task<Object> task = new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                springContext = SpringApplication.run(CinemaApplication.class, argument);
                return null;
            }
        };
        task.setOnSucceeded((WorkerStateEvent e) -> {
            //Inicializacja kontrolerów dla wszystkich widoków
            ChooseSeatController chooseSeatController = springContext.getBean(ChooseSeatController.class);
            SummaryControler summaryControler = springContext.getBean(SummaryControler.class);
            ChooseMovieController chooseMovieController = springContext.getBean(ChooseMovieController.class);
            ChooseSeanceController chooseSeanceController = springContext.getBean(ChooseSeanceController.class);
            MovieInfoController movieInfoController = springContext.getBean(MovieInfoController.class);

            PageController pageContainer = new PageController();

            //Podpinanie do pageContainer wszystkich stron które będą kiedykolwiek wczytane.
            pageContainer.loadPageWithContorller(CinemaApplication.pageChooseSeat, CinemaApplication.pageChooseSeatFile, chooseSeatController);
            pageContainer.loadPageWithContorller(CinemaApplication.pageSummary, CinemaApplication.pageSummaryFile, summaryControler);
            pageContainer.loadPageWithContorller(CinemaApplication.pageChooseMovie, CinemaApplication.pageChooseMovieFile, chooseMovieController);
            pageContainer.loadPageWithContorller(CinemaApplication.pageChooseSeance, CinemaApplication.pageChooseSeanceFile, chooseSeanceController);
            pageContainer.loadPageWithContorller(CinemaApplication.pageMovieInfo, CinemaApplication.pageMovieInfoFile, movieInfoController);
            //Ustawienie strony która ma być wyświetlona w stage'u
            pageContainer.setPage(CinemaApplication.pageMovieInfo);
            movieInfoController.stage(primaryStage);

            BorderPane root = new BorderPane();
            root.setCenter(pageContainer);
            //root.getChildren().addAll(pageContainer);
            Scene scene = new Scene(root);
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.F12 && !primaryStage.isFullScreen()) {
                    primaryStage.setFullScreen(true);
                }
                else if(event.getCode() == KeyCode.F12 && primaryStage.isFullScreen()){
                        primaryStage.setFullScreen(false);
                }
            });
            primaryStage.setMinHeight(800);
            primaryStage.setMinWidth(620);
            primaryStage.setMaxHeight(1080);
            primaryStage.setMaxWidth(1920);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Cinewatch");
            primaryStage.show();
        });
        task.setOnFailed(e -> {
            System.exit(0);
            Platform.exit();
        });
        task.run();
    }



    public static void main(String[] args) throws Exception {
        CinemaApplication.argument = args;
        launch(args);
    }


}









