package com.cinema;

import com.cinema.controller.ChooseSeatController;
import com.cinema.controller.PageController;
import com.cinema.controller.SummaryControler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class CinemaApplication extends Application {


    private static String[] argument;
    private ApplicationContext springContext = null;

    //Tutaj dodać wszystkie pliki FXML oraz nadać im unikalne ID
    public static String pageChooseSeat = "pageChooseSeat";
    public static String pageChooseSeatFile = "scene/ChooseSeat.fxml";

    public static String pageSummary = "pageSummary";
    public static String pageSummaryFile = "scene/Summary.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Task<Object> task = new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                springContext = SpringApplication.run(CinemaApplication.class, argument);
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            //Inicializacja kontrolerów dla wszystkich widoków
            ChooseSeatController chooseSeatController = springContext.getBean(ChooseSeatController.class);
            SummaryControler summaryControler = springContext.getBean(SummaryControler.class);

            PageController pageContainer = new PageController();

            //Podpinanie do pageContainer wszystkich stron które będą kiedykolwiek wczytane.
            pageContainer.loadPageWithContorller(CinemaApplication.pageChooseSeat, CinemaApplication.pageChooseSeatFile, chooseSeatController);
            pageContainer.loadPageWithContorller(CinemaApplication.pageSummary, CinemaApplication.pageSummaryFile, summaryControler);

            //Ustawienie strony która ma być wyświetlona w stage'u
            pageContainer.setPage(CinemaApplication.pageChooseSeat);

            BorderPane root = new BorderPane();
            root.setCenter(pageContainer);
            //root.getChildren().addAll(pageContainer);
            Scene scene = new Scene(root);
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









