package application;

import java.io.FileNotFoundException;
import java.io.IOException;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Management;
import view.PrimaryView;
import view.AbstractView;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException, ClassNotFoundException, IOException {

		Management model = new Management();
		AbstractView view = new PrimaryView(primaryStage);
		Controller control = new Controller(model, view);

	}

	public static void main(String[] args) {
		System.out.println("hhh");
		launch(args);
	}
}
