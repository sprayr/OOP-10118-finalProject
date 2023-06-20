package view;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import listeners.UIListener;

public class CreateTestManuallyView implements AbstractCreateTestManuallyView {
	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	Label countLabel, questIndexLabel, testSizeLabel;
	TextField questIndexText, testSizeText;
	Button displayValidQuestButton, chooseQuestionIndexButton, chooseTestSizeButton;
	boolean res;
	int questIndex, testSize, count;

	public CreateTestManuallyView(Stage stage) {
		window = stage;
		window.setTitle("Create a Test Manually");
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

		countLabel = new Label("please enter question index until your test is ready!");
		questIndexLabel = new Label("Question index:");
		testSizeLabel = new Label("Test Size (num of questions):");

		questIndexText = new TextField();
		testSizeText = new TextField();

		displayValidQuestButton = new Button("Display all valid questions");
		chooseQuestionIndexButton = new Button("Add Question");
		chooseTestSizeButton = new Button("Choose size");

		chooseTestSizeButton.setOnAction(e -> {
			try {
				testSize = Integer.parseInt(testSizeText.getText());
				count = testSize;
				if (testSize > 0 && testSize <= getNumOfValidQuestions()) {
					createTest(testSize);
					questIndexText.setDisable(false);
					chooseQuestionIndexButton.setDisable(false);
					countLabel.setVisible(true);
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
					questIndexText.setDisable(true);
					countLabel.setVisible(false);
					chooseQuestionIndexButton.setDisable(true);

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
					questIndexText.setDisable(true);
					countLabel.setVisible(false);
					chooseQuestionIndexButton.setDisable(true);
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
				questIndexText.setDisable(true);
				countLabel.setVisible(false);
				chooseQuestionIndexButton.setDisable(true);

			}

		});

		chooseQuestionIndexButton.setOnAction(e -> {
			try {

				res = false;
				questIndex = Integer.parseInt(questIndexText.getText());

				if (questIndex > 0 && questIndex <= getNumOfQuestions()) {
					if (isQuestionValid(--questIndex)) {
						res = addQuestionToTest(questIndex);
						System.out.println(getNumOfQuestions());
						System.out.println(getNumOfValidQuestions());
						if (res) {
							System.out.println(getNumOfQuestions());
							System.out.println(getNumOfValidQuestions());
							count--;
							if (count > 0) {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Question added!");
								alert.setHeaderText("Please add " + count + " more questions!");
								alert.setContentText("Please enter add more valid questions before creating a test!");
								alert.showAndWait().ifPresent(rs -> {
									if (rs == ButtonType.OK) {
										System.out.println("Pressed OK.");
									}
								});
							}

							if (count == 0) {
								finish();
								testSizeText.clear();
								questIndexText.setDisable(true);
								countLabel.setVisible(false);
								chooseQuestionIndexButton.setDisable(true);
							}
						}
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error!");
						alert.setHeaderText("This Question cannot be added to test");
						alert.setContentText(
								"Please enter an index of a valid question from the list, that wasn't added to the test already!");
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Pressed OK.");
							}
						});
					}

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("The number you entered is not in range");
					alert.setContentText("Please enter an index of a valid question from the list!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});

				}
			} catch (Exception exception) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Wrong input");
				alert.setContentText(
						"Please enter an index of a valid question from the list, that wasn't added to the test already!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
			}
		});

		displayValidQuestButton.setOnAction(e -> {
			OpenAllValidQuestionView();
		});

		questIndexText.setDisable(true);
		chooseQuestionIndexButton.setDisable(true);
		countLabel.setVisible(false);

		displayValidQuestButton.setMinWidth(150);
		chooseTestSizeButton.setMinWidth(120);
		chooseQuestionIndexButton.setMinWidth(120);
		countLabel.setMinWidth(300);

	}

	public boolean addQuestionToTest(int questIndex) {
		boolean b = false;
		for (UIListener l : listeners) {
			b = l.addQuestionToTest(questIndex);
		}
		return b;
	}

	public int getNumOfQuestions() {
		int numOfQuestions = 0;
		for (UIListener l : listeners) {
			numOfQuestions = l.getNumOfQuestions();
		}
		return numOfQuestions;
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
		GridPane.setConstraints(questIndexLabel, 0, 1);
		GridPane.setConstraints(questIndexText, 1, 1);
		GridPane.setConstraints(chooseQuestionIndexButton, 2, 1);
		GridPane.setConstraints(displayValidQuestButton, 0, 3);
		GridPane.setConstraints(countLabel, 2, 3);

		GridPane.setHalignment(testSizeLabel, HPos.RIGHT);
		GridPane.setValignment(testSizeLabel, VPos.CENTER);
		GridPane.setHalignment(testSizeText, HPos.CENTER);
		GridPane.setValignment(testSizeText, VPos.CENTER);
		GridPane.setHalignment(chooseTestSizeButton, HPos.CENTER);
		GridPane.setValignment(chooseTestSizeButton, VPos.CENTER);
		GridPane.setHalignment(questIndexLabel, HPos.RIGHT);
		GridPane.setValignment(questIndexLabel, VPos.CENTER);
		GridPane.setHalignment(chooseQuestionIndexButton, HPos.CENTER);
		GridPane.setValignment(chooseQuestionIndexButton, VPos.CENTER);
		GridPane.setHalignment(displayValidQuestButton, HPos.CENTER);
		GridPane.setValignment(displayValidQuestButton, VPos.CENTER);
		GridPane.setValignment(countLabel, VPos.CENTER);
		GridPane.setHalignment(countLabel, HPos.RIGHT);

		gp.getChildren().addAll(countLabel, questIndexLabel, testSizeLabel, questIndexText, testSizeText,
				displayValidQuestButton, chooseQuestionIndexButton, chooseTestSizeButton);

		gp.setAlignment(Pos.CENTER);

		gp.setHgap(5);
		gp.setVgap(10);

	}

	@Override
	public void OpenAllValidQuestionView() {
		for (UIListener l : listeners) {
			l.OpenAllValidQuestionView(new Stage());
		}
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

	public int getNumOfValidQuestions() {
		int i = 0;
		for (UIListener l : listeners) {
			i = l.getNumOfValidQuestions();
		}
		return i;

	}
}
