package view;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import listeners.UIListener;
import model.MultiChoiceQuestion;
import model.OpenQuestion;
import model.Question;

public class AddQuestionView implements AbstractAddQuestionView {
	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	Label choiceLabel, multiChoiceQuestLabel, openQuestLabel, answerLabel;
	TextField openQuestText, openAnswerText, multiQuestText;
	ChoiceBox<String> choiceButton;
	Button displayQuestButton, addButton, openAddAnswerViewButton;

	public AddQuestionView(Stage stage) {
		window = stage;
		window.setTitle("Add a Question");

		window.setResizable(false);
		buttons();
		gridPane();
		scene();

		window.show();

	}

	@Override
	public void registerListener(UIListener listener) {
		this.listeners.add(listener);
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

		GridPane.setConstraints(answerLabel, 0, 2);
		GridPane.setConstraints(choiceButton, 2, 0);
		GridPane.setConstraints(choiceLabel, 1, 0);
		GridPane.setConstraints(openQuestLabel, 0, 1);
		GridPane.setConstraints(multiChoiceQuestLabel, 2, 1);
		GridPane.setConstraints(openQuestText, 1, 1);
		GridPane.setConstraints(multiQuestText, 3, 1);
		GridPane.setConstraints(openAnswerText, 1, 2);
		GridPane.setConstraints(displayQuestButton, 1, 3);
		GridPane.setConstraints(addButton, 3, 2);
		GridPane.setConstraints(openAddAnswerViewButton, 2, 3);

		GridPane.setValignment(answerLabel, VPos.CENTER);
		GridPane.setValignment(choiceButton, VPos.CENTER);
		GridPane.setValignment(choiceLabel, VPos.CENTER);
		GridPane.setValignment(openQuestLabel, VPos.CENTER);
		GridPane.setValignment(multiChoiceQuestLabel, VPos.CENTER);
		GridPane.setHalignment(answerLabel, HPos.RIGHT);
		GridPane.setHalignment(choiceButton, HPos.LEFT);
		GridPane.setHalignment(choiceLabel, HPos.RIGHT);
		GridPane.setHalignment(openQuestLabel, HPos.RIGHT);
		GridPane.setHalignment(multiChoiceQuestLabel, HPos.RIGHT);
		GridPane.setHalignment(displayQuestButton, HPos.CENTER);
		GridPane.setValignment(displayQuestButton, VPos.CENTER);
		GridPane.setHalignment(addButton, HPos.CENTER);
		GridPane.setValignment(addButton, VPos.CENTER);
		GridPane.setHalignment(openAddAnswerViewButton, HPos.CENTER);
		GridPane.setValignment(openAddAnswerViewButton, VPos.CENTER);

		gp.getChildren().addAll(choiceLabel, multiChoiceQuestLabel, openQuestLabel, answerLabel, choiceButton,
				openQuestText, openAnswerText, multiQuestText, displayQuestButton, addButton, openAddAnswerViewButton);

		gp.setAlignment(Pos.CENTER);

		gp.setHgap(5);
		gp.setVgap(10);

	}

	@Override
	public void buttons() {
		choiceButton = new ChoiceBox<String>();
		choiceButton.setValue("");
		ObservableList<String> list = choiceButton.getItems();
		list.add("Open Question");
		list.add("Multi Choice Question");

		choiceLabel = new Label("Select a Question Type:");
		Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);

		choiceLabel.setFont(font);

		openQuestLabel = new Label("Open Question:");
		multiChoiceQuestLabel = new Label("Multi Choice Question:");
		answerLabel = new Label("Answer:");

		openAddAnswerViewButton = new Button("Open Add Answer Window");
		openQuestText = new TextField();
		multiQuestText = new TextField();
		openAnswerText = new TextField();
		addButton = new Button("Add Question");

		multiQuestText.setDisable(true);
		openAnswerText.setDisable(true);
		openQuestText.setDisable(true);
		addButton.setDisable(true);
		displayQuestButton = new Button("Display all questions");
		displayQuestButton.setOnAction(e -> {
			OpenAllQuestionView();
		});

		openAddAnswerViewButton.setOnAction(e -> {
			openAddAnswerView();
		});

		addButton.setOnAction(e -> {
			Question temp;
			if (choiceButton.getSelectionModel().getSelectedItem().equals("Open Question")) {
				String questText = openQuestText.getText();
				String answerText = openAnswerText.getText();
				if (questText.length() != 0 && answerText.length() != 0) {
					System.out.println(questText);
					temp = new OpenQuestion(questText, answerText);
					if (addQuestionToController(temp)) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Question added");
						alert.setHeaderText("yay!!!");
						alert.setContentText("Your Question was added succesfully");
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Pressed OK.");
							}
						});
					} else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Question was not added");
						alert.setHeaderText("Error");
						alert.setContentText("Your Question already exist in the database!");
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Pressed OK.");
							}
						});

					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Text warning");
					alert.setHeaderText("There is an empty field");
					alert.setContentText("Please enter text to field");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});

				}

			} else {
				String questText = multiQuestText.getText();
				if (questText.length() > 0) {
					try {
						temp = new MultiChoiceQuestion(questText);
						if (addQuestionToController(temp)) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Question added");
							alert.setHeaderText("Yay!!!");
							alert.setContentText(
									"Your Question was added succesfully, \nnow you can add answer in the add answers section");
							alert.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK) {
									System.out.println("Pressed OK.");
								}
							});
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Question was not added");
							alert.setHeaderText("Error");
							alert.setContentText("Your Question already exist in the database!");
							alert.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK) {
									System.out.println("Pressed OK.");
								}
							});

						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Text warning");
					alert.setHeaderText("There is an empty field");
					alert.setContentText("Please enter text to field");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}

			}
			openQuestText.clear();
			multiQuestText.clear();
			openAnswerText.clear();

		});
		addButton.setMinWidth(150);
		displayQuestButton.setMinWidth(150);

		choiceButton.setOnAction(e -> {
			if (choiceButton.getSelectionModel().getSelectedItem().equals("Open Question")) {
				addButton.setDisable(false);
				multiQuestText.setDisable(true);
				openAnswerText.setDisable(false);
				openQuestText.setDisable(false);
			} else {
				addButton.setDisable(false);
				multiQuestText.setDisable(false);
				openAnswerText.setDisable(true);
				openQuestText.setDisable(true);
			}
		});

	}

	public void OpenAllQuestionView() {
		for (UIListener l : listeners) {
			l.OpenAllQuestionView(new Stage());
		}
	}

	@Override
	public boolean addQuestionToController(Question quest) {
		boolean res = false;
		for (UIListener l : listeners) {
			res = l.addQuestionToModel(quest);
		}
		return res;
	}

	public void openAddAnswerView() {
		for (UIListener l : listeners) {
			l.openAddAnswerView(new Stage());
		}

	}

}
