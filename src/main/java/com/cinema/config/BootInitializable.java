package com.cinema.config;

import com.cinema.util.PageController;
import javafx.fxml.Initializable;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Dominik on 14.04.2017.
 */
public interface BootInitializable extends Initializable, ApplicationContextAware {
    /**
     * setParrentPage it's a method that allows to get main container for pages in other controllers.
     *
     * @param parentPage represents root element of MainStage where pages are displayed.
     */
    void setPageParrent(PageController parentPage);
}
