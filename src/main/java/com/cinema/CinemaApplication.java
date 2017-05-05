package com.cinema;

import com.cinema.controller.ChooseMovieController;
import com.cinema.controller.HomeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class CinemaApplication extends Application {


	private static String [] argument;
	private ApplicationContext springContext = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		/*Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
		primaryStage.setTitle("STRAŻNICYYYY");
		primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();*/


		Task<Object> task = new Task<Object>(){
			@Override
			protected Object call() throws Exception {
				springContext = SpringApplication.run(CinemaApplication.class, argument);
				return null;
			}
		};
		task.setOnSucceeded(e -> {
			ChooseMovieController controller = springContext.getBean(ChooseMovieController.class);
			Parent parent = (Parent) controller.initView();
			Scene scene = new Scene(parent);

			primaryStage.setResizable(false);
			primaryStage.setScene(scene);

			primaryStage.setTitle("Aplikacja Kinowa");
			primaryStage.show();
		});
		task.setOnFailed(e -> {
			System.exit(0);
			Platform.exit();
		});
		task.run();

	}


	public static void main(String[] args) throws Exception {

		CinemaApplication.argument = args;
		launch(args);
	}




}









