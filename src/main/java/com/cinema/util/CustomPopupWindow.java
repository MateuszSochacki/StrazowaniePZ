package com.cinema.util;

import com.cinema.config.BootInitializable;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

/**
 * Created by Damrod on 15.05.2017.
 * This class allows to create custom "popup window" in selected container and blur the background of "targetBlur".
 */

public class CustomPopupWindow {

    private BorderPane mainPanel;
    private int width, height;
    private StackPane parent;
    private Node targetBlur;

    public boolean isDoFadeTransition() {
        return doFadeTransition;
    }

    public void setDoFadeTransition(boolean doFadeTransition) {
        this.doFadeTransition = doFadeTransition;
    }

    private boolean doFadeTransition;

    public boolean isAnimate() {
        return animate;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    private boolean animate = true;

    /**Constructor.
     * @param width - an integer value in pixels of min width for popup window.
     * @param height - an integer value in pixels of min width for popup window.
     * @param parent - an StackPane nude where popup will be displayed.
     * @param targetBlur - Node element which will have Blur effect applied on.
     */
    public CustomPopupWindow(int width, int height, StackPane parent, Node targetBlur){
        doFadeTransition = true;
        this.width = width;
        this.height=height;
        this.parent = parent;
        this.targetBlur = targetBlur;

        mainPanel = new BorderPane();
        mainPanel.setCacheHint(CacheHint.SPEED);
        mainPanel.setMaxSize(width, height);
        mainPanel.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        mainPanel.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        mainPanel.setStyle("-fx-background-color: whitesmoke;" +
                "-fx-border-color: #333333;"+
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        Hyperlink textClose = new Hyperlink("X");
        textClose.setFocusTraversable(false);
        textClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closePopupWindow();
            }
        });
        mainPanel.setTop(textClose);
        mainPanel.setAlignment(mainPanel.getTop(), Pos.TOP_RIGHT);
        mainPanel.setPadding(new Insets(12, 12, 12, 12));

    }
    /**
     * this method opens custom popup.
     */
    public void openPopupWindow() {
        targetBlur.setDisable(true);

        GaussianBlur blur = new GaussianBlur(0);
        targetBlur.setEffect(blur);

        DoubleProperty valueBlurRadius = new SimpleDoubleProperty(0);
        valueBlurRadius.addListener((observable, oldV, newV) ->
        {
            blur.setRadius(newV.doubleValue());
        });
        Timeline timeline = new Timeline();
        final KeyValue kv = new KeyValue(valueBlurRadius, 30);
        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();


        if(doFadeTransition) {
            FadeTransition ft = new FadeTransition();
            ft.setNode(mainPanel);
            ft.setDuration(new Duration(500));
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            parent.getChildren().add(mainPanel);
            ft.play();
        }else {
            parent.getChildren().add(mainPanel);
        }

    }

    /**
     * this method closes custom popup.
     */
    public void closePopupWindow() {

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

        if(doFadeTransition) {
            final DoubleProperty opacity = mainPanel.opacityProperty();
            Timeline fade = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                    new KeyFrame(new Duration(500), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            parent.getChildren().remove(mainPanel);
                            targetBlur.setDisable(false);
                        }
                    }, new KeyValue(opacity, 0.0)));
            fade.play();
        }
        else {
            parent.getChildren().remove(mainPanel);
            targetBlur.setDisable(false);
        }
    }

    public BorderPane getMainPanel() {
        return mainPanel;
    }

}
