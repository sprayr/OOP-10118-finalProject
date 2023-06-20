package view;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import listeners.UIListener;

public class CreateTestRandomlyView implements AbstractCreateTestRandomlyView {
	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	Label testSizeLabel;
	TextField testSizeText;
	Button displayValidQuestButton, chooseTestSizeButton;
	boolean res;
	int questIndex, testSize;

	public CreateTestRandomlyView(Stage stage) {
		window = stage;
		window.setTitle("Create a Test Randomly");
		window.setResizable(false);
		buttons();
		gridPane();
		scene();

		window.show();

	}

	@Override
	public void registerListener(UIListener listener) {
		listeners.add(listener);
	}

	@Override
	public void buttons() {

		testSizeLabel = new Label("Test Size (num of questions):");

		testSizeText = new TextField();

		displayValidQuestButton = new Button("Display all valid questions");
		chooseTestSizeButton = new Button("Choose size");

		chooseTestSizeButton.setOnAction(e -> {
			try {
				testSize = Integer.parseInt(testSizeText.getText());
				if (testSize > 0 && testSize <= getNumOfValidQuestions()) {
					createTest(testSize);
					for (int i = 0; i < testSize; i++) {
						if (!addQuestionToTest(randomQuestIndex())) {
							i--;
						}

					}
					finish();

				} else if (getNumOfValidQuestions() == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("There are no Valid Questions!!");
					alert.setContentText("Please add more valid questions before creating a test!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});

					testSizeText.clear();

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("The number you entered is not in range");
					alert.setContentText("Please try again!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
					testSizeText.clear();
				}
			} catch (Exception exception) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Wrong input");
				alert.setContentText("Please enter a size that is in range of valid question number!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
				testSizeText.clear();

			}

		});

		displayValidQuestButton.setOnAction(e -> {
			OpenAllValidQuestionView();
		});

		displayValidQuestButton.setMinWidth(150);
		chooseTestSizeButton.setMinWidth(120);

	}

	@Override
	public void scene() {
		scene = new Scene(gp, 600, 200);
		window.setScene(scene);
	}

	@Override
	public void gridPane() {
		gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));

		int rowCount = 4;
		int columnCount = 3;

		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(100d / rowCount);

		for (int i = 0; i < rowCount; i++) {
			gp.getRowConstraints().add(rc);
		}

		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(100d / columnCount);

		for (int i = 0; i < columnCount; i++) {
			gp.getColumnConstraints().add(cc);
		}

		GridPane.setConstraints(testSizeLabel, 0, 0);
		GridPane.setConstraints(testSizeText, 1, 0);
		GridPane.setConstraints(chooseTestSizeButton, 2, 0);
		GridPane.setConstraints(displayValidQuestButton, 0, 3);

		GridPane.setHalignment(testSizeLabel, HPos.RIGHT);
		GridPane.setValignment(testSizeLabel, VPos.CENTER);
		GridPane.setHalignment(testSizeText, HPos.CENTER);
		GridPane.setValignment(testSizeText, VPos.CENTER);
		GridPane.setHalignment(chooseTestSizeButton, HPos.CENTER);
		GridPane.setValignment(chooseTestSizeButton, VPos.CENTER);
		GridPane.setHalignment(displayValidQuestButton, HPos.CENTER);
		GridPane.setValignment(displayValidQuestButton, VPos.CENTER);

		gp.getChildren().addAll(testSizeLabel, testSizeText, displayValidQuestButton, chooseTestSizeButton);

		gp.setAlignment(Pos.CENTER);

		gp.setHgap(5);
		gp.setVgap(10);

	}

	@Override
	public boolean isQuestionValid(int questIndex) {
		boolean res = false;
		for (UIListener l : listeners) {
			res = l.isValidQuestion(questIndex);
		}
		return res;
	}

	@Override
	public void createTest(int size) {
		for (UIListener l : listeners) {
			l.createTest(size);
		}
	}

	@Override
	public void finish() throws FileNotFoundException {
		for (UIListener l : listeners) {
			l.finishTest();
		}
	}

	@Override
	public int getNumOfValidQuestions() {
		int i = 0;
		for (UIListener l : listeners) {
			i = l.getNumOfValidQuestions();
		}
		return i;

	}

	@Override
	public int getNumOfQuestions() {
		int numOfQuestions = 0;
		for (UIListener l : listeners) {
			numOfQuestions = l.getNumOfQuestions();
		}
		return numOfQuestions;
	}

	@Override
	public boolean addQuestionToTest(int questIndex) throws Exception {
		boolean b = false;
		for (UIListener l : listeners) {
			b = l.addQuestionToTestRandomly(questIndex);
		}
		return b;
	}

	@Override
	public int randomQuestIndex() {
		return (int) (Math.random() * getNumOfQuestions());
	}

	@Override
	public void OpenAllValidQuestionView() {
		for (UIListener l : listeners) {
			l.OpenAllValidQuestionView(new Stage());
		}
	}

}
