package com.cinema;

import com.cinema.controller.Controller;
import com.cinema.config.Config;
import com.cinema.dao.PriceEntityDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "dao")
public class CinemaApplication extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		/*Parent root = FXMLLoader.load(getClass().getResource("/CinemaApplication.fxml"));
		primaryStage.setTitle("STRAŻNICYYYY");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();*/


	}

	public static void main(String[] args) throws Exception {

		SpringApplication.run(CinemaApplication.class, args); //test

		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		Controller controller = context.getBean(Controller.class);
		controller.test();

		//launch(args);
	}




}









