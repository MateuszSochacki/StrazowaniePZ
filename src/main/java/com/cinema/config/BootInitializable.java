package com.cinema.config;

import com.cinema.PageController;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Dominik on 14.04.2017.
 */
public interface BootInitializable extends Initializable, ApplicationContextAware {

    void initConstruct();
    void setPageParrent(PageController parentPage);

    void stage(Stage primaryStage);

    Node initView();
}
