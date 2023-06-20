package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import listeners.*;
import model.Question;

public class PrimaryView implements AbstractView {

	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;

	Button displayQuestButton, addQuestButton, addAnswerButton, changeQuestButton, changeAnswerButton,
			deleteAnswerButton, createTestManuallyButton, createRandomTestButton, cloneTestButton, exitButton;

	public PrimaryView(Stage stage) {
		window = stage;
		window.setTitle("Main Menu");
		window.setHeight(600);
		window.setWidth(340);
		window.setResizable(false);
		buttons();
		gridPane();
		scene();

		window.setScene(scene);
		window.show();

	}

	public void buttons() {
		displayQuestButton = new Button("Display all questions");
		addQuestButton = new Button("Add a question");
		addAnswerButton = new Button("Add an answer to question");
		changeQuestButton = new Button("Change a question");
		changeAnswerButton = new Button("Change an answer");
		deleteAnswerButton = new Button("Delete an answer");
		createTestManuallyButton = new Button("Create a test manually");
		createRandomTestButton = new Button("Create a test randomly");
		cloneTestButton = new Button("Clone an existing test");
		exitButton = new Button("Exit");

		displayQuestButton.setMaxWidth(250);
		addQuestButton.setMaxWidth(250);
		addAnswerButton.setMaxWidth(250);
		changeQuestButton.setMaxWidth(250);
		changeAnswerButton.setMaxWidth(250);
		deleteAnswerButton.setMaxWidth(250);
		createTestManuallyButton.setMaxWidth(250);
		createRandomTestButton.setMaxWidth(250);
		cloneTestButton.setMaxWidth(250);
		exitButton.setMaxWidth(100);

		displayQuestButton.setOnAction(e -> {
			openAllQuestionView();
		});
		addQuestButton.setOnAction(e -> {
			openAddQuestionView();

		});
		addAnswerButton.setOnAction(e -> {
			openAddAnswerView();

		});
		changeQuestButton.setOnAction(e -> {
			openChangeQuestionView();

		});
		changeAnswerButton.setOnAction(e -> {
			openChangeAnswerView();
		});
		deleteAnswerButton.setOnAction(e -> {
			openDeleteAnswerView();
		});
		createTestManuallyButton.setOnAction(e -> {
			openCreateTestManuallyView();
		});
		createRandomTestButton.setOnAction(e -> {
			openCreateTestRandomly();

		});
		cloneTestButton.setOnAction(e -> {
			openCloneTestView();

		});
		exitButton.setOnAction(e -> {
			try {
				saveAndExit();
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Something went wrong!!");
				alert.setContentText("We were not able to save youer questions");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
			}

		});

	}

	private void saveAndExit() throws FileNotFoundException, IOException {
		for (UIListener l : listeners) {
			l.saveAndExit();
		}
	}

	private void openCloneTestView() {
		for (UIListener l : listeners) {
			l.openCloneTestView(new Stage());
		}
	}

	private void openCreateTestRandomly() {
		for (UIListener l : listeners) {
			l.openCreateTestRandomlyView(new Stage());
		}
	}

	private void openCreateTestManuallyView() {
		for (UIListener l : listeners) {
			l.openCreateTestManuallyView(new Stage());
		}
	}

	private void openDeleteAnswerView() {
		for (UIListener l : listeners) {
			l.openDeleteAnswerView(new Stage());
		}
	}

	public void openChangeAnswerView() {
		for (UIListener l : listeners) {
			l.openChanegeAnswerView(new Stage());
		}
	}

	public void openAddAnswerView() {
		for (UIListener l : listeners) {
			l.openAddAnswerView(new Stage());
		}
	}

	public void openAddQuestionView() {
		for (UIListener l : listeners) {
			l.OpenAddQuestionView(new Stage());
		}
	}

	public void openAllQuestionView() {
		for (UIListener l : listeners) {
			l.OpenAllQuestionView(new Stage());
		}
	}

	public void scene() {
		scene = new Scene(gp, 600, 340);
		scene.setFill(Color.LIGHTGRAY);
	}

	public void gridPane() {
		gp = new GridPane();

		gp.setAlignment(Pos.CENTER);

		gp.add(displayQuestButton, 1, 1);
		gp.add(addQuestButton, 1, 2);
		gp.add(addAnswerButton, 1, 3);
		gp.add(changeQuestButton, 1, 4);
		gp.add(changeAnswerButton, 1, 5);
		gp.add(deleteAnswerButton, 1, 6);
		gp.add(createTestManuallyButton, 1, 7);
		gp.add(createRandomTestButton, 1, 8);
		gp.add(cloneTestButton, 1, 9);
		gp.add(exitButton, 1, 10);

		GridPane.setHalignment(displayQuestButton, HPos.CENTER);
		GridPane.setValignment(displayQuestButton, VPos.CENTER);
		GridPane.setHalignment(addQuestButton, HPos.CENTER);
		GridPane.setValignment(addQuestButton, VPos.CENTER);
		GridPane.setHalignment(addAnswerButton, HPos.CENTER);
		GridPane.setValignment(addAnswerButton, VPos.CENTER);
		GridPane.setHalignment(changeQuestButton, HPos.CENTER);
		GridPane.setValignment(changeQuestButton, VPos.CENTER);
		GridPane.setHalignment(changeAnswerButton, HPos.CENTER);
		GridPane.setValignment(changeAnswerButton, VPos.CENTER);
		GridPane.setHalignment(deleteAnswerButton, HPos.CENTER);
		GridPane.setValignment(deleteAnswerButton, VPos.CENTER);
		GridPane.setHalignment(createTestManuallyButton, HPos.CENTER);
		GridPane.setValignment(createTestManuallyButton, VPos.CENTER);
		GridPane.setHalignment(createRandomTestButton, HPos.CENTER);
		GridPane.setValignment(createRandomTestButton, VPos.CENTER);
		GridPane.setHalignment(cloneTestButton, HPos.CENTER);
		GridPane.setValignment(cloneTestButton, VPos.CENTER);
		GridPane.setHalignment(exitButton, HPos.CENTER);
		GridPane.setValignment(exitButton, VPos.CENTER);

		gp.setVgap(20);

	}

	@Override
	public void registerListener(UIListener listener) {
		listeners.add(listener);
	}

	@Override
	public void openChangeQuestionView() {
		for (UIListener l : listeners) {
			l.OpenChangeQuestionView(new Stage());
		}
	}

	public void closeWindow() {
		Platform.exit();
	}

}
