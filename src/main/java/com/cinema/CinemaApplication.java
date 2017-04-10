package com.cinema;

import com.model.CategoryEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.config.*;
import org.springframework.orm.jpa.EntityManagerHolder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


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
		////halo

		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);


		/*EntityManagerFactory managerFactory = context.getBean(EntityManagerFactory.class);

		EntityManager manager = managerFactory.createEntityManager();
		Session session = manager.unwrap(Session.class);
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setName("FANTAZI");
		session.saveOrUpdate(categoryEntity);*/

		((AnnotationConfigApplicationContext)context).close();
		// KURWA NIKOS ZYJEMY CHLOPIE


		launch(args);
	}




}









