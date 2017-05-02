package com.cinema.controller;

import com.cinema.config.BootInitializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dominik on 14.04.2017.
 */
@Component
public class FormCategoryController implements BootInitializable {

    private ApplicationContext springContext;

    @Autowired
    private HomeController homeController;

    @Override
    public void initConstruct() {

    }

    @Override
    public void stage(Stage primaryStage) {

    }

    @Override
    public Node initView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/scene/inner/category/Form.fxml"));
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

    public void doCancel(ActionEvent event){
        homeController.onClickCategory(event);
    }
}
