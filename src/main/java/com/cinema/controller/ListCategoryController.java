package com.cinema.controller;

import com.cinema.config.BootInitializable;
import com.cinema.services.CategoryRepository;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ListCategoryController implements BootInitializable {
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<CategoryEntity> tableView;
    @FXML
    private TableColumn<CategoryEntity, Integer> columnId;
    @FXML
    private TableColumn<CategoryEntity, String> columnName;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;


    @Autowired
    private FormCategoryController formCategoryController;

    @Autowired
    private HomeController homeController;

    @Autowired
    private CategoryRepository categoryRepository;

    private ApplicationContext springContext;

    public void setFields(CategoryEntity entity) {
        txtId.setText(Integer.toString(entity.getIdCategory()));
        txtName.setText(entity.getName());
    }

    private void clearFields(){
        txtId.clear();
        txtName.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends CategoryEntity> values, CategoryEntity oldValue, CategoryEntity newValue) -> {
            if(newValue != null){
                setFields(newValue);
            } else {
                clearFields();
            }
        });
        columnId.setCellValueFactory(new PropertyValueFactory<CategoryEntity, Integer>("idCategory"));
        columnName.setCellValueFactory(new PropertyValueFactory<CategoryEntity, String>("name"));
    }

    @Override
    public void initConstruct() {
        tableView.getItems().clear();
        tableView.getItems().addAll(categoryRepository.findAll());
    }

    @Override
    public void stage(Stage primaryStage) {

    }

    @Override
    public Node initView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/scene/inner/category/ListCategory.fxml"));
            loader.setController(springContext.getBean(this.getClass()));

            return loader.load();
        } catch (IOException e) {
            System.err.println("Can't load scene");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    public void doAdd(ActionEvent actionEvent) {
        homeController.setCenterLayout(formCategoryController.initView());
    }
}
