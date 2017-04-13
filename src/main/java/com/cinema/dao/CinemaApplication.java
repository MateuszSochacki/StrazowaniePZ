package com.cinema.dao;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@SpringBootApplication
public class CinemaApplication extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/CinemaApplication.fxml"));
		primaryStage.setTitle("STRAŻNICYYYY");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();


	}

	public static void main(String[] args) throws Exception {

		SpringApplication.run(CinemaApplication.class, args); //test

		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		Controller controller = context.getBean(Controller.class);
		controller.test();

		((AnnotationConfigApplicationContext)context).close();
		launch(args);
	}




}









