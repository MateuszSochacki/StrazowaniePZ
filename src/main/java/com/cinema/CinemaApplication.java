package com.cinema;

import com.cinema.controller.ChooseSeatController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class CinemaApplication extends Application {


	private static String [] argument;
	private ApplicationContext springContext = null;

	public static String pageChooseSeat = "pageChooseSeat";
	public static String pageChooseSeatFile = "scene/ChooseSeat.fxml";

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
			//TO DO fix controller!
			ChooseSeatController controller = springContext.getBean(ChooseSeatController.class);

			ChooseSeatController contr  = springContext.getBean(ChooseSeatController.class);

			PageController pageContainer = new PageController();

			pageContainer.loadPageWithContorller(CinemaApplication.pageChooseSeat, CinemaApplication.pageChooseSeatFile, controller);

			pageContainer.setPage(CinemaApplication.pageChooseSeat);


			Group root = new Group();
			root.getChildren().addAll(pageContainer);
			Scene scene = new Scene(root, 1280, 720);

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Aplikacja kinowa");
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









