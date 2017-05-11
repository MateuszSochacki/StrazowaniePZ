package com.cinema.controller;

import com.cinema.config.BootInitializable;
import com.cinema.controller.ChooseSeatController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * Created by Damrod on 09.05.2017.
 */
public class PageController extends StackPane {

    private HashMap<String, Node> pages = new HashMap<>();

    public HashMap<String, Node> getPages() {
        return pages;
    }

    public PageController(){
        super();
    }
    //add page to collection
    public void addPage(String name, Node page){
        pages.put(name, page);
    }


    //get page from collection
    public Node getPage(String name){
        return pages.get(name);
    }

    public boolean loadPage(String name, String res){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(res));
            Parent loadPage = (Parent) fxmlLoader.load();
            BootInitializable currentPage  = (BootInitializable) fxmlLoader.getController();
            currentPage.setPageParrent(this);
            addPage(name, loadPage);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean loadPageWithContorller(String name, String res, BootInitializable controller){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(res));
            fxmlLoader.setController(controller);
            Parent loadPage = (Parent) fxmlLoader.load();
            BootInitializable currentPage  = (BootInitializable) fxmlLoader.getController();
            currentPage.setPageParrent(this);
            addPage(name, loadPage);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setPage(final String name){
        if(pages.get(name) != null){
            final DoubleProperty opacity = opacityProperty();

            if(!getChildren().isEmpty()){
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(500), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                getChildren().remove(0);
                                getChildren().add(0, pages.get(name));
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame((new Duration(500)), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        },new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                setOpacity(0.0);
                getChildren().add(pages.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        }else{
            System.out.println("Page couldn't be loaded");
            return false;
        }
    }

    public boolean unloadScreen(String name){
        if(pages.remove(name)==null) {
            System.out.println("Page didn't exist");
            return false;
        }else {
            return true;
        }
    }

}
