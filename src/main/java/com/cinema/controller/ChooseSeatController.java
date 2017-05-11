package com.cinema.controller;

import com.cinema.CinemaApplication;
import com.cinema.config.BootInitializable;
import com.cinema.model.CinemaHallEntity;
import com.cinema.model.SeanceEntity;
import com.cinema.model.SeatEntity;
import com.cinema.services.SeanceRepository;
import com.cinema.services.SeatRepository;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
 * Created by Dominik on 05.05.2017.
 *
 *
 */
@Component
public class ChooseSeatController implements BootInitializable {

    private ApplicationContext springContext;

    @FXML
    private BorderPane homeLayout;

    @FXML
    private GridPane gridPane;

    private PageController pageController;

    private int cinemaHallArray[][];

    private Scene currentScene;

    private static double scale = 1.5;

    private List<TilePaneCustom> reservedSeats = new ArrayList<>();

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private SeatRepository seatRepository;

    private CinemaHallEntity cinemaHall;

    @Override
    public void initConstruct() {


        List<SeanceEntity> seance = seanceRepository.findAll();

        //dla testów pobiera pierwszy element z listy seansów, żeby odczytać obiekt typu CinemaHallEntity, który jest wymagany
        //do znalezienia siedzien.
        cinemaHall = seance.get(0).getCinemaHall();

        List<SeatEntity> seats = seatRepository.findByCinemaHall(cinemaHall);

        //TODO: Read from file

        //stworzenie "mapy" kina
        cinemaHallArray = new int[][]{
                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
        };

        for (SeatEntity seat : seats) {

            cinemaHallArray[seat.getRow()][seat.getNumber()] = 2;
        }

        generateEmptyGrid();

        fillGridWithData();


        gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                EventTarget target = event.getTarget();
                if (target.getClass() == TilePaneCustom.class) {
                    reservSeats(event);
                }
            }
        });


    }

    @Override
    public void setPageParrent(PageController parentPage) {
        pageController = parentPage;
    }

    /**
     * Na podstawie pobranych danych wypełnia siatkę tworząc dla każdego miejsca obiekt typu TilePaneCustom z odpowiednimi parametrami
     */

    private void fillGridWithData() {

        for (int i = 0; i <= 15; i++) {
            int number = 1;

            for (int j = 0; j <= 16; j++) {
                if (j == 16) {
                    if (i != 0) {
                        gridPane.add(new Label(String.valueOf(i)), j, i);
                    }
                } else {

                    if (cinemaHallArray[i][j] == 1) {
                        Label label = new Label(String.valueOf(number));
                        label.setDisable(true);
                        number++;

                        TilePaneCustom pane = new TilePaneCustom();
                        pane.setId(String.valueOf(i) + "," + String.valueOf(j));
                        pane.setMaxHeight(22*scale);
                        pane.setMaxWidth(22*scale);
                        pane.getChildren().add(label);
                        pane.setStyle("-fx-background-color: #8ae6ef");
                        pane.setRow();
                        pane.setColumn();
                        gridPane.add(pane, j, i);
                        GridPane.setHalignment(pane, HPos.CENTER);
                    } else if (cinemaHallArray[i][j] == 2) {

                        Label label = new Label(String.valueOf(number));
                        number++;
                        TilePaneCustom pane = new TilePaneCustom();
                        pane.setId(String.valueOf(i) + "," + String.valueOf(j));
                        pane.setMaxHeight(22*scale);
                        pane.setMaxWidth(22*scale);
                        pane.getChildren().add(label);
                        pane.setStyle("-fx-background-color: #ffd7a1");
                        gridPane.add(pane, j, i);
                        pane.setDisable(true);
                        GridPane.setHalignment(pane, HPos.CENTER);

                    } else if (cinemaHallArray[i][j] == 9) {
                        TilePaneCustom pane = new TilePaneCustom();
                        pane.setId(String.valueOf(i) + "," + String.valueOf(j));
                        pane.setMaxHeight(5*scale);
                        pane.setMaxWidth(40*scale);
                        pane.setStyle("-fx-background-color: #b6b6b6");
                        gridPane.add(pane, j, i);
                        pane.setDisable(true);
                        GridPane.setValignment(pane, VPos.TOP);
                    } else {
                        TilePaneCustom pane = new TilePaneCustom();
                        pane.setId(String.valueOf(i) + "," + String.valueOf(j));
                        pane.setMaxHeight(22*scale);
                        pane.setMaxWidth(22*scale);
                        pane.setStyle("-fx-background-color: #ffffff");
                        gridPane.add(pane, j, i);
                        pane.setDisable(true);
                        GridPane.setHalignment(pane, HPos.CENTER);
                    }
                }
            }
        }
    }

    /**
     * Generuje pustą siatkę typu GridPane. Domyślnie ustawiona na maksymalną wartość, czyli 15x16. Ostatnia kolumna odpowiada
     * oznaczeniu, który to numer rzędu.
     */

    private void generateEmptyGrid() {

        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i <= 15; i++) {
            ColumnConstraints column = new ColumnConstraints(25*scale, 25*scale, 25*scale);
            gridPane.getColumnConstraints().add(column);
        }
        for (int i = 1; i <= 16; i++) {

            if (i == 1) {
                RowConstraints row = new RowConstraints(40*scale, 40*scale, 40*scale);
                gridPane.getRowConstraints().add(row);
            } else {
                RowConstraints row = new RowConstraints(25*scale, 25*scale, 25*scale);
                gridPane.getRowConstraints().add(row);
            }

        }
    }

    /**
     * Metoda wywoływana w momencie kliknięcia na obiekt typu TilePaneCustom, który nie jest zablokowany, czyli tylko i wyłącznie
     * miejsca wolne. Obiekt tego typu dodatkowo posiada pole isClicked, które informuje o jego stanie:
     * clicked = true oznacza, że jest to miejce zarezerwowane
     * W momencie kliknięcia, w zależnosci od pola isClicked, siedzenie jest dodawana bądź usuwane z listy siedzeń + zostaje zmieniony kolor
     */
    private void reservSeats(MouseEvent event) {
        String id = event.getPickResult().getIntersectedNode().getId();
        System.out.println(id);

        currentScene = gridPane.getScene();

        TilePaneCustom pane = (TilePaneCustom) currentScene.lookup("#" + id);

        if (pane.isClicked) {
            pane.setStyle("-fx-background-color: #8ae6ef");
            pane.isClicked = false;
            reservedSeats.remove(pane);

        } else {
            pane.setStyle("-fx-background-color: #c7ffa6");
            pane.isClicked = true;
            reservedSeats.add(pane);
        }
    }

    @Override
    public void stage(Stage primaryStage) {
    }

    @Override
    public Node initView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/scene/ChooseSeat.fxml"));
            loader.setController(springContext.getBean(this.getClass()));

            return loader.load();
        } catch (IOException e) {
            System.err.println("Can't load scene");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       this.initConstruct();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    public class TilePaneCustom extends TilePane {

        private boolean isClicked = false;
        private int row;
        private int column;

        public void setRow() {
            String data[] = getId().split(",");
            String row = data[0];
            this.row = new Integer(row);
        }

        public void setColumn() {
            String data[] = getId().split(",");
            String column = data[1];

            this.column = new Integer(column);
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }

        public boolean isClicked() {
            return isClicked;
        }

        public void setClicked(boolean clicked) {
            isClicked = clicked;
        }

    }


    /**
     * Metoda wywoływana w momencie kliknięcia przycisku "Dalej"
     *
     * @param event to jakiś parametr który trzeba dodać, bo nie działa bez parametru
     */
    public void btnSubmit(MouseEvent event) {

        System.out.println("Click!");

        for (TilePaneCustom tilePaneCustom : reservedSeats) {
            SeatEntity seat = new SeatEntity();
            seat.setRow(tilePaneCustom.getRow());
            seat.setNumber(tilePaneCustom.getColumn());
            seat.setCinemaHall(cinemaHall);

            seatRepository.save(seat);
        }
        pageController.setPage(CinemaApplication.pageSummary);
    }
}