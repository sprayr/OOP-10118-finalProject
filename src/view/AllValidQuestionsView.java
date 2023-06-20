package view;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import listeners.UIListener;

public class AllValidQuestionsView implements AbstractQuestionView {
	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	TextArea ta;

	public AllValidQuestionsView(Stage stage) {
		window = stage;
		window.setTitle("All Valid Questions");
		window.setMinWidth(500);
		textArea();

		scene();

		window.show();

	}

	@Override
	public void registerListener(UIListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void scene() {
		scene = new Scene(ta, 200, 800);
		window.setScene(scene);
	}

	@Override
	public void textArea() {
		ta = new TextArea();
		ta.setEditable(false);
		ta.setWrapText(true);

	}

	@Override
	public void insertAllQuestionsToUI(String string) {
		ta.appendText(string);
		if (string.contains("There are no valid questions!")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Warning");
			alert.setHeaderText("something went wrong");
			alert.setContentText("There are no valid questions in database!");
			alert.showAndWait().ifPresent(rs -> {
				if (rs == ButtonType.OK) {
					System.out.println("Pressed OK.");
					window.close();
				}
			});

		}
	}

	@Override
	public void closeWindow() {
		window.close();
	}

}
