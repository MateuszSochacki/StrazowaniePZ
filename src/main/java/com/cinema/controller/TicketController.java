package com.cinema.controller;

import com.cinema.config.BootInitializable;
import com.cinema.util.PageController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dominik on 05.06.2017.
 * this class creates a ticket and makes it visible to user by adding views to mainGridPane.
 */
public class TicketController implements BootInitializable {

    private ApplicationContext springContext;
    private PageController pageController;

    @Override
    public void setPageParrent(PageController parentPage) {
    pageController = parentPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setViews(Text title, Text info, Text price, ComboBox combobox){
    mainGridPane.getChildren().clear();
    mainGridPane.add(title, 0,0);
    mainGridPane.add(combobox, 1,0);
    mainGridPane.add(info, 0,1);
    mainGridPane.add(price, 1,1);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    springContext = applicationContext;
    }

    @FXML
    private GridPane mainGridPane;
}
