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

public class AddAnswerView implements AbstractAddAnswerView {
	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	Label correctLabel, indexLabel, answerLabel;
	TextField questIndexText, answerText;
	ChoiceBox<String> correctButton;
	Button displayQuestButton, addButton, chooseIndexButton;
	boolean res;
	int index;

	public AddAnswerView(Stage stage) {
		window = stage;
		window.setTitle("Add an Answer");
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
		correctButton.setValue("");
		ObservableList<String> list = correctButton.getItems();
		list.add("True");
		list.add("False");

		correctLabel = new Label("Correct:");

		indexLabel = new Label(" Question index:");
		answerLabel = new Label("Answer:");

		questIndexText = new TextField();
		answerText = new TextField();
		addButton = new Button("Add");
		chooseIndexButton = new Button("Choose Question");

		chooseIndexButton.setOnAction(e -> {
			try {
				index = Integer.parseInt(questIndexText.getText());

				if (index > 0 && index <= getNumOfQuestions()) {
					answerText.setDisable(false);
					addButton.setDisable(false);
					if (!isOpenQuestion(--index)) {
						correctButton.setDisable(false);
					} else {
						correctButton.setDisable(true);
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
					addButton.setDisable(true);

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
				addButton.setDisable(true);
			}
		});

		answerText.setDisable(true);
		correctButton.setDisable(true);
		addButton.setDisable(true);
		displayQuestButton = new Button("Display all questions");
		displayQuestButton.setOnAction(e -> {
			OpenAllQuestionView();
		});

		addButton.setOnAction(e -> {
			try {
				boolean res = false;
				if (answerText.getText().length() > 0) {
					if (isOpenQuestion(index)) {
						res = addAnswerToModel(index, answerText.getText(), true);
					} else {
						if (correctButton.getSelectionModel().getSelectedItem().equals("True")) {
							res = addAnswerToModel(index, answerText.getText(), true);
						} else if (correctButton.getSelectionModel().getSelectedItem().equals("False")) {
							res = addAnswerToModel(index, answerText.getText(), false);

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
					alert.setContentText("Your answer was added successfully!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("Wrong input");
					alert.setContentText(
							"make sure your answer does not exist already, and there are less than 10 answers!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Could not add answer");
				alert.setContentText("We are sorry, we could not add your answer!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
			}

			questIndexText.clear();
			answerText.clear();
			addButton.setDisable(true);
			correctButton.setValue("");
			answerText.setDisable(true);
			correctButton.setDisable(true);
		});

		addButton.setMinWidth(140);
		displayQuestButton.setMinWidth(140);

	}

	public boolean addAnswerToModel(int index, String text, boolean b) throws Exception {
		boolean res = false;
		for (UIListener l : listeners) {

			res = l.addAnswerToModel(index, text, b);
		}
		return res;
	}

	public boolean isOpenQuestion(int i) {
		boolean res = false;
		for (UIListener l : listeners) {
			res = l.isOpenQuestion(i);
		}
		return res;

	}

	public int getNumOfQuestions() {
		int numOfQuestions = 0;
		for (UIListener l : listeners) {
			numOfQuestions = l.getNumOfQuestions();
		}
		return numOfQuestions;
	}

	public void OpenAllQuestionView() {
		for (UIListener l : listeners) {
			l.OpenAllQuestionView(new Stage());
		}

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

		GridPane.setConstraints(answerLabel, 0, 1);
		GridPane.setConstraints(correctLabel, 0, 2);
		GridPane.setConstraints(indexLabel, 0, 0);
		GridPane.setConstraints(questIndexText, 1, 0);
		GridPane.setConstraints(answerText, 1, 1);
		GridPane.setConstraints(displayQuestButton, 1, 3);
		GridPane.setConstraints(addButton, 2, 3);
		GridPane.setConstraints(correctButton, 1, 2);
		GridPane.setConstraints(chooseIndexButton, 2, 0);

		GridPane.setValignment(answerLabel, VPos.CENTER);
		GridPane.setValignment(correctLabel, VPos.CENTER);
		GridPane.setValignment(indexLabel, VPos.CENTER);
		GridPane.setHalignment(answerLabel, HPos.RIGHT);
		GridPane.setHalignment(correctLabel, HPos.RIGHT);
		GridPane.setHalignment(indexLabel, HPos.RIGHT);
		GridPane.setHalignment(displayQuestButton, HPos.CENTER);
		GridPane.setValignment(displayQuestButton, VPos.CENTER);
		GridPane.setHalignment(addButton, HPos.CENTER);
		GridPane.setValignment(addButton, VPos.CENTER);
		GridPane.setHalignment(correctButton, HPos.LEFT);
		GridPane.setValignment(correctButton, VPos.CENTER);
		GridPane.setHalignment(chooseIndexButton, HPos.CENTER);
		GridPane.setValignment(chooseIndexButton, VPos.CENTER);

		gp.getChildren().addAll(correctLabel, indexLabel, answerLabel, questIndexText, answerText, displayQuestButton,
				addButton, correctButton, chooseIndexButton);

		gp.setAlignment(Pos.CENTER);

		gp.setHgap(5);
		gp.setVgap(10);

	}

}
