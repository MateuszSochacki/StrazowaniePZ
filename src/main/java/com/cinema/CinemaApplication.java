package com.cinema;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CinemaApplication extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/CinemaApplication.fxml"));
		primaryStage.setTitle("STRAŻNICYYYY");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();

	} //halo hjgjhg

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CinemaApplication.class, args); //test

		launch(args);
	}

}
