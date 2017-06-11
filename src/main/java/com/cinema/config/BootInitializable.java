package com.cinema.config;

import com.cinema.util.PageController;
import javafx.fxml.Initializable;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Dominik on 14.04.2017.
 */
public interface BootInitializable extends Initializable, ApplicationContextAware {
    void setPageParrent(PageController parentPage);
}
