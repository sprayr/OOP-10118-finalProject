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

public class DeleteAnswerView implements AbstractDeleteAnswerView {
	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	Label questionIndexLabel, answerIndexLabel;
	TextField questIndexText, answerIndexText;
	Button displayQuestButton, deleteButton, chooseQuestionIndexButton, chooseAnswerIndexButton;
	boolean res;
	int questIndex, answerIndex;

	public DeleteAnswerView(Stage stage) {
		window = stage;
		window.setTitle("Delete an Answer");
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

		answerIndexLabel = new Label("Answer index:");
		questionIndexLabel = new Label("Question index:");

		questIndexText = new TextField();
		answerIndexText = new TextField();
		deleteButton = new Button("Delete");
		chooseQuestionIndexButton = new Button("Choose Question");
		chooseAnswerIndexButton = new Button("Choose Answer");

		chooseQuestionIndexButton.setOnAction(e -> {
			try {
				questIndex = Integer.parseInt(questIndexText.getText());

				if (questIndex > 0 && questIndex <= getNumOfQuestions()) {
					deleteButton.setDisable(false);
					if (!isOpenQuestion(--questIndex)) {
						answerIndexText.setDisable(false);
						chooseAnswerIndexButton.setDisable(false);
					} else {
						answerIndexText.setDisable(true);
						chooseAnswerIndexButton.setDisable(true);
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
					deleteButton.setDisable(true);

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
				questIndexText.clear();
				deleteButton.setDisable(true);
				chooseAnswerIndexButton.setDisable(true);
			}
		});
		answerIndexText.setDisable(true);
		answerIndexText.setDisable(true);
		deleteButton.setDisable(true);
		displayQuestButton = new Button("Display all questions");

		displayQuestButton.setOnAction(e -> {
			OpenAllQuestionView();
		});

		deleteButton.setOnAction(e -> {
			try {
				boolean res = false;

				if (isOpenQuestion(questIndex)) {
					res = deleteAnswerFromModel(questIndex, -10);
				} else {
					res = deleteAnswerFromModel(questIndex, --answerIndex);
				}

				if (res) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Answer deleted");
					alert.setHeaderText("yayyy!");
					alert.setContentText("The answer was removed successfully!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("Wrong input");
					alert.setContentText("make sure your answer exist");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Could not delete the answer");
				alert.setContentText("We are sorry, we could not delete your answer!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
			}

			questIndexText.clear();
			answerIndexText.clear();
			deleteButton.setDisable(true);
			answerIndexText.setDisable(true);
			chooseAnswerIndexButton.setDisable(true);

		});

		chooseAnswerIndexButton.setOnAction(e -> {
			try {
				answerIndex = Integer.parseInt(answerIndexText.getText());
				if (answerIndex > 2 && answerIndex <= getNumOfAnswersForQuestion(questIndex)) {
					deleteButton.setDisable(false);
				} else if (answerIndex <= 2 && answerIndex > 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("You cannot remove a default answer!!");
					alert.setContentText(
							"Please enter an index of an answer from the question you chose (Larger than 2)!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
					answerIndexText.clear();
					deleteButton.setDisable(true);

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
					deleteButton.setDisable(true);

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
				deleteButton.setDisable(true);

			}

		});

		deleteButton.setMinWidth(140);
		displayQuestButton.setMinWidth(140);
		answerIndexText.setDisable(true);
		deleteButton.setDisable(true);
		chooseAnswerIndexButton.setDisable(true);
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
		int columnCount = 4;

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

		GridPane.setConstraints(chooseAnswerIndexButton, 2, 1);
		GridPane.setConstraints(answerIndexLabel, 0, 1);
		GridPane.setConstraints(questionIndexLabel, 0, 0);
		GridPane.setConstraints(questIndexText, 1, 0);
		GridPane.setConstraints(answerIndexText, 1, 1);
		GridPane.setConstraints(displayQuestButton, 1, 3);
		GridPane.setConstraints(deleteButton, 2, 3);
		GridPane.setConstraints(chooseQuestionIndexButton, 2, 0);

		GridPane.setValignment(answerIndexLabel, VPos.CENTER);
		GridPane.setHalignment(answerIndexLabel, HPos.RIGHT);
		GridPane.setValignment(questionIndexLabel, VPos.CENTER);
		GridPane.setHalignment(questionIndexLabel, HPos.RIGHT);
		GridPane.setHalignment(displayQuestButton, HPos.CENTER);
		GridPane.setValignment(displayQuestButton, VPos.CENTER);
		GridPane.setHalignment(deleteButton, HPos.CENTER);
		GridPane.setValignment(deleteButton, VPos.CENTER);
		GridPane.setHalignment(chooseQuestionIndexButton, HPos.CENTER);
		GridPane.setValignment(chooseQuestionIndexButton, VPos.CENTER);
		GridPane.setHalignment(chooseAnswerIndexButton, HPos.CENTER);
		GridPane.setValignment(chooseAnswerIndexButton, VPos.CENTER);

		gp.getChildren().addAll(questionIndexLabel, answerIndexLabel, questIndexText, answerIndexText,
				displayQuestButton, deleteButton, chooseQuestionIndexButton, chooseAnswerIndexButton);

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
	public boolean deleteAnswerFromModel(int questIndex, int answerIndex) throws Exception {
		boolean res = false;
		for (UIListener l : listeners) {

			res = l.deleteAnswer(questIndex, answerIndex);
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

	public int getNumOfAnswersForQuestion(int questIndex) {
		int numOfAnswers = 0;
		for (UIListener l : listeners) {
			numOfAnswers = l.getNumOfAnswersForQuestion(questIndex);
		}
		return numOfAnswers;
	}

}
