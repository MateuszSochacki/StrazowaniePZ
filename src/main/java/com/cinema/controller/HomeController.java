package com.cinema.controller;

import com.cinema.PageController;
import com.cinema.config.BootInitializable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by msoch_000 on 03-04-2017.
 */


@Component
public class HomeController implements BootInitializable {

    @Autowired
    private ListCategoryController listCategoryController;

    @FXML
    private BorderPane homeLayout;


    private ApplicationContext springContext;

    public void setCenterLayout(Node node){
        this.homeLayout.setCenter(node);
        this.homeLayout.autosize();
    }

    @Override
    public void initConstruct() {

    }

    @Override
    public void setPageParrent(PageController parentPage) {

    }


    @Override
    public void stage(Stage primaryStage) {

    }

    @Override
    public Node initView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/scene/Home.fxml"));
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

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    public void onClickCategory(ActionEvent actionEvent) {
        setCenterLayout(listCategoryController.initView());
        listCategoryController.initConstruct();

    }

    public void onClickClose(ActionEvent actionEvent){
        Platform.exit();
    }
}
