package view;

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

public class ChangeAnswerView implements AbstractChangeAnswerView {

	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	Label correctLabel, questIndexLabel, answerLabel, answerIndexLabel;
	TextField questIndexText, answerText, answerIndexText;
	ChoiceBox<String> correctButton;
	Button displayQuestButton, changeButton, chooseQuestionIndexButton, chooseAnswerIndexButton;
	boolean res;
	int questIndex, answerIndex;

	public ChangeAnswerView(Stage stage) {
		window = stage;
		window.setTitle("Change an Answer");
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
		correctButton = new ChoiceBox<String>();
		correctButton.setValue("True");
		ObservableList<String> list = correctButton.getItems();
		list.add("True");
		list.add("False");

		correctLabel = new Label("Correct:");

		questIndexLabel = new Label("Question index:");
		answerLabel = new Label("New Answer:");
		answerIndexLabel = new Label("Answer Index:");

		questIndexText = new TextField();
		answerText = new TextField();
		answerIndexText = new TextField();
		changeButton = new Button("Change");
		chooseQuestionIndexButton = new Button("Choose Question");
		chooseAnswerIndexButton = new Button("Choose Answer");

		chooseAnswerIndexButton.setOnAction(e -> {
			try {
				answerIndex = Integer.parseInt(answerIndexText.getText());
				if (answerIndex > 2 && answerIndex <= getNumOfAnswersForQuestion(questIndex)) {
					answerText.setDisable(false);
					correctButton.setDisable(false);
					changeButton.setDisable(false);
				} else if (answerIndex <= 2 && answerIndex > 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("You cannot change a default answer!!");
					alert.setContentText(
							"Please enter an index of an answer from the question you chose (Larger than 2)!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
					answerIndexText.clear();
					answerText.setDisable(true);
					correctButton.setDisable(true);
					changeButton.setDisable(true);

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("The number you entered is not in range");
					alert.setContentText("Please enter an index of an answer from the question you chose!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
					answerIndexText.clear();
					answerText.setDisable(true);
					correctButton.setDisable(true);
					changeButton.setDisable(true);

				}
			} catch (Exception exception) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Wrong input");
				alert.setContentText("Please enter an index of an answer from the question you chose!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});

				answerIndexText.clear();
				answerText.setDisable(true);
				correctButton.setDisable(true);
				changeButton.setDisable(true);

			}

		});

		chooseQuestionIndexButton.setOnAction(e -> {
			try {
				questIndex = Integer.parseInt(questIndexText.getText());
				if (questIndex > 0 && questIndex <= getNumOfQuestions()) {
					if (!isOpenQuestion(--questIndex)) {
						changeButton.setDisable(true);
						correctButton.setDisable(true);
						answerText.setDisable(true);
						chooseAnswerIndexButton.setDisable(false);
						answerIndexText.setDisable(false);
					} else {
						correctButton.setDisable(true);
						chooseAnswerIndexButton.setDisable(true);
						answerIndexText.setDisable(true);
						answerText.setDisable(false);
						changeButton.setDisable(false);
					}

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("The number you entered is not in range");
					alert.setContentText("Please enter an index of a question from the list!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
					questIndexText.clear();
					answerText.setDisable(true);
					changeButton.setDisable(true);
					correctButton.setDisable(true);
					chooseAnswerIndexButton.setDisable(true);
					answerIndexText.setDisable(true);

				}
			} catch (Exception exception) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Wrong input");
				alert.setContentText("Please enter an index of a question from the list!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});

				answerText.setDisable(true);
				questIndexText.clear();
				changeButton.setDisable(true);
				correctButton.setDisable(true);
				chooseAnswerIndexButton.setDisable(true);
				answerIndexText.setDisable(true);
			}
		});
		chooseAnswerIndexButton.setDisable(true);
		answerIndexText.setDisable(true);
		answerText.setDisable(true);
		correctButton.setDisable(true);
		changeButton.setDisable(true);
		displayQuestButton = new Button("Display all questions");
		displayQuestButton.setOnAction(e -> {
			OpenAllQuestionView();
		});

		changeButton.setOnAction(e -> {
			try {
				boolean res = false;
				if (answerText.getText().length() > 0) {
					if (isOpenQuestion(questIndex)) {
						res = changeAnswerToModel(questIndex, -1, answerText.getText(), true);
					} else {
						if (correctButton.getSelectionModel().getSelectedItem().equals("True")) {
							res = changeAnswerToModel(questIndex, --answerIndex, answerText.getText(), true);
						} else if (correctButton.getSelectionModel().getSelectedItem().equals("False")) {
							res = changeAnswerToModel(questIndex, --answerIndex, answerText.getText(), false);
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error!");
							alert.setHeaderText("Wrong input");
							alert.setContentText("Please select if the answer is correct!");
							alert.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK) {
									System.out.println("Pressed OK.");
								}
							});

						}
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("Wrong input");
					alert.setContentText("Please enter the answer!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}
				if (res) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Answer added");
					alert.setHeaderText("yayyy!");
					alert.setContentText("Your answer was change successfully!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
					questIndexText.clear();
					answerText.clear();

				} else if (answerText.getText().length() > 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("Wrong input");
					alert.setContentText("make sure your answer does not exist already "
							+ ((isOpenQuestion(questIndex)) ? "" : "and there are less than 10 answers!"));
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}

			} catch (Exception exception) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Could not change answer");
				alert.setContentText("We are sorry, we could not change your answer!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
			}

			changeButton.setDisable(true);
			answerText.setDisable(true);
			correctButton.setDisable(true);
		});

		changeButton.setMinWidth(150);
		displayQuestButton.setMinWidth(150);
		chooseAnswerIndexButton.setMinWidth(120);
		chooseQuestionIndexButton.setMinWidth(120);

	}

	public int getNumOfAnswersForQuestion(int questIndex) {
		int numOfAnswers = 0;
		for (UIListener l : listeners) {
			numOfAnswers = l.getNumOfAnswersForQuestion(questIndex);
		}
		return numOfAnswers;
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

		int rowCount = 6;
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

		GridPane.setConstraints(answerLabel, 0, 2);
		GridPane.setConstraints(correctLabel, 0, 3);
		GridPane.setConstraints(questIndexLabel, 0, 0);
		GridPane.setConstraints(questIndexText, 1, 0);
		GridPane.setConstraints(answerText, 1, 2);
		GridPane.setConstraints(displayQuestButton, 0, 5);
		GridPane.setConstraints(changeButton, 1, 5);
		GridPane.setConstraints(correctButton, 1, 3);
		GridPane.setConstraints(chooseQuestionIndexButton, 2, 0);
		GridPane.setConstraints(answerIndexLabel, 0, 1);
		GridPane.setConstraints(answerIndexText, 1, 1);
		GridPane.setConstraints(chooseAnswerIndexButton, 2, 1);

		GridPane.setValignment(answerLabel, VPos.CENTER);
		GridPane.setValignment(correctLabel, VPos.CENTER);
		GridPane.setValignment(questIndexLabel, VPos.CENTER);
		GridPane.setHalignment(answerLabel, HPos.RIGHT);
		GridPane.setHalignment(correctLabel, HPos.RIGHT);
		GridPane.setHalignment(questIndexLabel, HPos.RIGHT);
		GridPane.setHalignment(displayQuestButton, HPos.CENTER);
		GridPane.setValignment(displayQuestButton, VPos.CENTER);
		GridPane.setHalignment(changeButton, HPos.CENTER);
		GridPane.setValignment(changeButton, VPos.CENTER);
		GridPane.setHalignment(correctButton, HPos.LEFT);
		GridPane.setValignment(correctButton, VPos.CENTER);
		GridPane.setHalignment(chooseQuestionIndexButton, HPos.CENTER);
		GridPane.setValignment(chooseQuestionIndexButton, VPos.CENTER);
		GridPane.setHalignment(chooseAnswerIndexButton, HPos.CENTER);
		GridPane.setValignment(chooseAnswerIndexButton, VPos.CENTER);
		GridPane.setHalignment(answerIndexText, HPos.CENTER);
		GridPane.setValignment(answerIndexText, VPos.CENTER);
		GridPane.setHalignment(answerIndexLabel, HPos.RIGHT);
		GridPane.setValignment(answerIndexLabel, VPos.CENTER);

		gp.getChildren().addAll(correctLabel, questIndexLabel, answerLabel, questIndexText, answerText,
				displayQuestButton, changeButton, correctButton, chooseQuestionIndexButton, answerIndexLabel,
				answerIndexText, chooseAnswerIndexButton);

		gp.setAlignment(Pos.CENTER);

		gp.setHgap(5);
		gp.setVgap(10);

	}

	@Override
	public void OpenAllQuestionView() {
		for (UIListener l : listeners) {
			l.OpenAllQuestionView(new Stage());
		}
	}

	@Override
	public boolean isOpenQuestion(int i) {
		boolean res = false;
		for (UIListener l : listeners) {
			res = l.isOpenQuestion(i);
		}
		return res;
	}

	@Override
	public boolean changeAnswerToModel(int questIndex, int answerIndex, String answer, boolean isTrue)
			throws Exception {
		System.out.println("changeAnswer.changanswer");
		boolean res = false;
		for (UIListener l : listeners) {

			res = l.changeAnswerToModel(questIndex, answerIndex, answer, isTrue);
		}
		return res;
	}

	@Override
	public int getNumOfQuestions() {
		int numOfQuestions = 0;
		for (UIListener l : listeners) {
			numOfQuestions = l.getNumOfQuestions();
		}
		return numOfQuestions;
	}
}
