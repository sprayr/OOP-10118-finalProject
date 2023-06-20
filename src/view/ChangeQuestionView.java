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

public class ChangeQuestionView implements AbstractChangeQuestionView {
	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	Label questionStrLabel, indexLabel;
	TextField questIndexText, questText;
	Button displayQuestButton, changeButton, chooseIndexButton;
	boolean res;
	int index;

	public ChangeQuestionView(Stage stage) {
		window = stage;
		window.setTitle("Change A Question");
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

		GridPane.setConstraints(questionStrLabel, 0, 1);
		GridPane.setConstraints(indexLabel, 0, 0);
		GridPane.setConstraints(questIndexText, 1, 0);
		GridPane.setConstraints(questText, 1, 1);
		GridPane.setConstraints(displayQuestButton, 1, 3);
		GridPane.setConstraints(changeButton, 2, 1);
		GridPane.setConstraints(chooseIndexButton, 2, 0);

		GridPane.setValignment(questionStrLabel, VPos.CENTER);
		GridPane.setValignment(indexLabel, VPos.CENTER);
		GridPane.setHalignment(questionStrLabel, HPos.RIGHT);
		GridPane.setHalignment(indexLabel, HPos.RIGHT);
		GridPane.setHalignment(displayQuestButton, HPos.CENTER);
		GridPane.setValignment(displayQuestButton, VPos.CENTER);
		GridPane.setHalignment(changeButton, HPos.CENTER);
		GridPane.setValignment(changeButton, VPos.CENTER);
		GridPane.setHalignment(chooseIndexButton, HPos.CENTER);
		GridPane.setValignment(chooseIndexButton, VPos.CENTER);

		gp.getChildren().addAll(questionStrLabel, indexLabel, questIndexText, questText, displayQuestButton,
				changeButton, chooseIndexButton);

		gp.setAlignment(Pos.CENTER);

		gp.setHgap(5);
		gp.setVgap(10);

	}

	@Override
	public void buttons() {

		questionStrLabel = new Label("New Question:");

		indexLabel = new Label("Question index:");

		questIndexText = new TextField();
		questText = new TextField();
		changeButton = new Button("Change Question");
		chooseIndexButton = new Button("Choose Question Index");

		chooseIndexButton.setOnAction(e -> {
			try {
				index = Integer.parseInt(questIndexText.getText());

				if (index > 0 && index <= getNumOfQuestions()) {
					questText.setDisable(false);
					changeButton.setDisable(false);

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
					changeButton.setDisable(true);

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
				changeButton.setDisable(true);
			}
		});

		questText.setDisable(true);
		changeButton.setDisable(true);
		displayQuestButton = new Button("Display all questions");
		displayQuestButton.setOnAction(e -> {
			OpenAllQuestionView();
		});

		changeButton.setOnAction(e -> {
			try {
				boolean res = false;
				if (questText.getText().length() > 0) {
					res = changeQuestion(--index, questText.getText());

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("Wrong input");
					alert.setContentText("Please enter the new question!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}
				if (res) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Question changed");
					alert.setHeaderText("Yayyy!");
					alert.setContentText("Your question was changed successfully!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("Wrong input");
					alert.setContentText("make sure your question does not exist already!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}

			} catch (Exception e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Could not change question");
				alert.setContentText("We are sorry, we could not change your question!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
			}

			questIndexText.clear();
			questText.clear();
			changeButton.setDisable(true);
			questText.setDisable(true);
		});

		changeButton.setMinWidth(150);
		displayQuestButton.setMinWidth(150);

	}

	@Override
	public void OpenAllQuestionView() {
		for (UIListener l : listeners) {
			l.OpenAllQuestionView(new Stage());
		}
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
	public boolean changeQuestion(int index, String str) {
		boolean changed = false;
		for (UIListener l : listeners) {
			changed = l.changeQuestion(index, str);
		}
		return changed;
	}

}
