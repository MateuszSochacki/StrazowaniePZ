package com.cinema.controller;

import com.cinema.CinemaApplication;
import com.cinema.config.BootInitializable;
import com.cinema.model.CinemaHallEntity;
import com.cinema.model.SeanceEntity;
import com.cinema.model.SeatEntity;
import com.cinema.model.Ticket;
import com.cinema.services.SeanceRepository;
import com.cinema.services.SeatRepository;
import com.cinema.util.CustomPopupWindow;
import com.cinema.util.DateConverter;
import com.cinema.util.MapReader;
import com.cinema.view.TicketView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 05.05.2017.
 */
@Component
public class ChooseSeatController implements BootInitializable {

    private SeanceEntity currentSeance;

    private ApplicationContext springContext;

    private List<TicketView> ticketsView;

    private PageController pageController;

    private int cinemaHallArray[][];

    private Scene currentScene;

    private static double scale = 1;

    private float sumPrice = 0;

    private List<TilePaneCustom> reservedSeats;

    @FXML
    private VBox summaryLayout;

    @FXML
    private Text textInfo;

    @FXML
    private GridPane gridPane;

    @FXML
    private Text price;

    @FXML
    private StackPane mainStackPane;

    @FXML
    private VBox mainVbox;

    @FXML
    void btnBackClicked(MouseEvent event) {
        pageController.setPage(CinemaApplication.pageChooseSeance);
    }

    /**
     * Metoda wywoływana w momencie kliknięcia przycisku "Dalej"
     *
     * @param event to jakiś parametr który trzeba dodać, bo nie działa bez parametru
     */

    @FXML
    void btnSubmitClicked(MouseEvent event) {
        System.out.println("Click!");
        List<SeatEntity> seatToSave = new ArrayList<>();
        List<SeatEntity> currentEntities = seatRepository.findAll();

        for (TilePaneCustom tilePaneCustom : reservedSeats) {
            boolean taken = false;
            SeatEntity seat = new SeatEntity();
            seat.setRow(tilePaneCustom.getRow());
            seat.setNumber(tilePaneCustom.getColumn());
            seat.setSeanceEntity(currentSeance);
            for (SeatEntity entity : currentEntities) {
                if (seat.getRow() == entity.getRow() && seat.getNumber() == entity.getNumber() && seat.getSeanceEntity().getIdSeance() == entity.getSeanceEntity().getIdSeance()) {
                    //TODO: create popup to inform about taken places
                    taken = true;
                    return;
                }
            }
            if (!taken) {
                seatToSave.add(seat);
            }
        }

        CustomPopupWindow popupWindow = new CustomPopupWindow(100, 100, mainStackPane, mainVbox);
        popupWindow.setDoFadeTransition(false);
        popupWindow.openPopupWindow();
        setPopUp(seatToSave, popupWindow);


        //pageController.setPage(CinemaApplication.pageSummary);
    }

    private void setPopUp(List<SeatEntity> seats, CustomPopupWindow popupWindow) {

        VBox mainBox = new VBox();
        mainBox.setPadding(new Insets(24, 24, 24, 24));
        mainBox.setSpacing(24);

        Text popupTitle = new Text("Podsumowanie zamówienia");
        popupTitle.setFont(Font.font("System", FontWeight.BOLD, 22));
        mainBox.getChildren().add(popupTitle);

        Text sumText = new Text("Do zapłaty: " + sumPrice+"zł");
        sumText.setFont(Font.font("System", FontWeight.BOLD, 22));
        sumText.setTextAlignment(TextAlignment.LEFT);
        Button button = new Button("Zapłać");
        button.setTextFill(Paint.valueOf("#ffff"));
        button.setFont(Font.font("System", FontWeight.BOLD, 18));
        button.setStyle("-fx-background-color:  #ea4646");
        button.setPadding(new Insets(8, 8, 8, 8));
        button.setPrefSize(150, 36);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (SeatEntity seat : seats){
                    seatRepository.save(seat);
                }
                pageController.setPage(CinemaApplication.pageChooseSeance);
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setPadding(new Insets(6,6,6,6));

        for (int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            grid.getColumnConstraints().add(column);
        }

        List<TicketView> tickets = new ArrayList<>();
        for (TicketView current : ticketsView) {
            tickets.add(current);
        }

        for (int i = 0; i < tickets.size(); i++) {
            for (int j = 0; j < tickets.size(); j++) {
                if ((i != j) && tickets.get(i).getTicket().equalsByType(tickets.get(j).getTicket())) {
                    tickets.remove(j);
                    j=0;
                }
            }
        }

        for (int i = 0; i <= tickets.size(); i++) {
            RowConstraints rowConstraints = new RowConstraints();
            grid.getRowConstraints().add(rowConstraints);
        }

        int row = 0;
        int column = 0;
        for (TicketView ticketView : tickets) {
            Text discount = new Text("Bilet \"" + ticketView.getTicket().getType() + "\"");
            discount.setFont(Font.font("System", 18));
            grid.add(discount, column, row);
            column++;
            int count = 0;
            for (int i = 0; i < ticketsView.size(); i++) {
                if (ticketsView.get(i).getTicket().getType() == ticketView.getTicket().getType()) {
                    count++;
                }
            }
            Text counter = new Text(String.valueOf(count)+" szt - "+ticketView.getTicket().getValue() * count + "zł");
            counter.setFont(Font.font("System", 18));
            counter.setFill(Paint.valueOf("#4a4a4a"));
            grid.add(counter, column, row);
            row++;
            column = 0;

        }

        mainBox.getChildren().add(grid);
        mainBox.getChildren().add(sumText);
        popupWindow.getMainPanel().setCenter(mainBox);
        popupWindow.getMainPanel().setBottom(button);
        popupWindow.getMainPanel().setAlignment(popupWindow.getMainPanel().getBottom(), Pos.CENTER_RIGHT);
    }

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private SeatRepository seatRepository;

    private CinemaHallEntity cinemaHall;


    @Autowired
    private MapReader mapReader;

    @Override
    public void initConstruct() {
        sumPrice = 0;
        price.setText("Do zapłaty: " + sumPrice);

        ticketsView = new ArrayList<>();
        reservedSeats = new ArrayList<>();

        cinemaHall = currentSeance.getCinemaHall();

        List<SeatEntity> seats = seatRepository.findBySeanceEntity(currentSeance);
        cinemaHallArray = mapReader.mapReader(cinemaHall.getCinemaHallType());


        //stworzenie "mapy" kina
//        cinemaHallArray = new int[][]{
//                {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9},
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
//                {0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
//                {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0},
//                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
//        };

        for (SeatEntity seat : seats) {

            cinemaHallArray[seat.getRow()][seat.getNumber()] = 2;
        }
        DateConverter dateConverter = new DateConverter();

        String date = dateConverter.convertDataToString(currentSeance.getDate());
        textInfo.setText(currentSeance.getMovie().getTitle() +"\n"+date+ "\nScena: " + currentSeance.getCinemaHall().getCinemaHallType());

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
                        pane.setMaxHeight(Region.USE_COMPUTED_SIZE);
                        pane.setMaxWidth(Region.USE_COMPUTED_SIZE);
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
                        pane.setMaxHeight(Region.USE_COMPUTED_SIZE);
                        pane.setMaxWidth(Region.USE_COMPUTED_SIZE);
                        pane.getChildren().add(label);
                        pane.setStyle("-fx-background-color: #ffd7a1");
                        gridPane.add(pane, j, i);
                        pane.setDisable(true);
                        GridPane.setHalignment(pane, HPos.CENTER);

                    } else if (cinemaHallArray[i][j] == 9) {
                        TilePaneCustom pane = new TilePaneCustom();
                        pane.setId(String.valueOf(i) + "," + String.valueOf(j));
                        pane.setMaxHeight(8);
                        pane.setMaxWidth(Region.USE_COMPUTED_SIZE);
                        pane.setStyle("-fx-background-color: #b6b6b6");
                        gridPane.add(pane, j, i);
                        pane.setDisable(true);
                        GridPane.setValignment(pane, VPos.TOP);
                    } else {
                        TilePaneCustom pane = new TilePaneCustom();
                        pane.setId(String.valueOf(i) + "," + String.valueOf(j));
                        pane.setMaxHeight(Region.USE_COMPUTED_SIZE);
                        pane.setMaxWidth(Region.USE_COMPUTED_SIZE);
                        pane.setStyle("-fx-background-color: #efefef");
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
        gridPane.setHgap(3); //horizontal gap in pixels => that's what you are asking for
        gridPane.setVgap(3); //vertical gap in pixels

        for (int i = 0; i <= 15; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(6.25);
            gridPane.getColumnConstraints().add(column);
        }
        for (int i = 1; i <= 16; i++) {

            if (i == 1) {
                RowConstraints row = new RowConstraints();
                row.setPercentHeight(6.25);
                gridPane.getRowConstraints().add(row);
            } else {
                RowConstraints row = new RowConstraints();
                row.setPercentHeight(6.25);
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
            removeTicket(pane);

        } else {
            pane.setStyle("-fx-background-color: #ccffa0");
            addTicket(pane);
            pane.isClicked = true;
            reservedSeats.add(pane);
        }
    }

    @Override
    public void stage(Stage primaryStage) {
    }

    private void addTicket(TilePaneCustom pane) {
        //  summaryLayout.getChildren().clear();
        Ticket ticket = setTicket(pane);


        TicketView view = new TicketView();
        view.setTicket(ticket);
        ticketsView.add(view);

        generateTicketsView(view);
        summaryLayout.getChildren().add(view.getLayout());
        changeSum();
    }

    private Ticket setTicket(TilePaneCustom pane) {
        Ticket ticket = new Ticket();
        Label nr = (Label) pane.getChildren().get(0);
        ticket.setRow(pane.getRow());
        ticket.setColumn(Integer.valueOf(nr.getText()));
        String id;
        id = currentSeance.getMovie().getTitle().substring(2,3).toUpperCase()+
                String.valueOf(new Random().nextInt(99999)+1251)+ currentSeance.getMovie().getTitle().substring(1,2).toUpperCase();
        ticket.setName("Bilet id: "+id +"\nrząd: " + pane.getRow() + ", miejsce: " + nr.getText());
        ticket.setType(Ticket.Abatement.Normalny);
        return ticket;
    }

    private void removeTicket(TilePaneCustom pane) {
        int index = 0;

        for (Iterator<TicketView> viewIterator = ticketsView.iterator(); viewIterator.hasNext(); ) {
            TicketView view = viewIterator.next();
            Label nr = (Label) pane.getChildren().get(0);
            int value = Integer.valueOf(nr.getText());
            if (pane.getRow() == view.getTicket().getRow() && value == view.getTicket().getColumn()) {
                summaryLayout.getChildren().remove(view.getLayout());
                viewIterator.remove();
            }
            index++;
        }
        changeSum();
    }

    private TicketController ticketController;

    private void generateTicketsView(TicketView view) {
        TicketController ticketController = new TicketController();
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getClass().getResource("/scene/Ticket.fxml"));
        fxmlLoader1.setController(ticketController);
        try {
            view.setLayout(fxmlLoader1.load());


            view.getLayout().setMargin(view.getLayout(), new Insets(0, 0, 10, 0));
            setTicketView(view, view.getTicket(), ticketController);
        } catch (IOException s) {
            s.printStackTrace();
        }

    }


    private void setTicketView(TicketView layout, Ticket ticket, TicketController ticketController) {
        Text title = new Text(ticket.getName());
        Text price = new Text(Float.toString(ticket.getValue()) + " zł");
        HBox hbox = new HBox();

        title.prefHeight(Region.USE_COMPUTED_SIZE);
        title.prefWidth(Region.USE_COMPUTED_SIZE);
        //layout.getLayout().setPadding(new Insets(4, 4, 4, 4));
        ComboBox<Ticket.Abatement> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(Ticket.Abatement.Normalny, Ticket.Abatement.Student, Ticket.Abatement.Uczeń, Ticket.Abatement.Emeryt);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        comboBox.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<Ticket.Abatement>() {
                    @Override
                    public void changed(ObservableValue<? extends Ticket.Abatement> observable, Ticket.Abatement oldValue, Ticket.Abatement newValue) {
                        if (newValue != null) {
                            float newPrice = setPrice(newValue, ticket);
                            price.setText(Float.toString(newPrice) + " zł");
                            changeSum();
                        }
                    }
                });
        ticketController.setViews(title, new Text(""), price, comboBox);
        //hbox.getChildren().addAll(title, comboBox);
        //layout.getLayout().getChildren().addAll(hbox, price);
    }


    private void changeSum() {
        sumPrice = 0;

        for (TicketView view : ticketsView) {
            sumPrice += view.getTicket().getValue();
        }
        this.price.setText("Suma: " + sumPrice + " zł");
    }

    private float setPrice(Ticket.Abatement abatement, Ticket ticket) {
        ticket.setType(abatement);
        return ticket.getValue();
    }

    @Override
    public Node initView() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("/scene/ChooseSeat.fxml"));
//            loader.setController(springContext.getBean(this.getClass()));
//
//            return loader.load();
//        } catch (IOException e) {
//            System.err.println("Can't load scene");
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initConstruct();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    public SeanceEntity getCurrentSeance() {
        return currentSeance;
    }

    public void setCurrentSeance(SeanceEntity currentSeance) {
        this.currentSeance = currentSeance;
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


}
