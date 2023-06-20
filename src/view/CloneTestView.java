package view;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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

public class CloneTestView implements AbstractCloneTestView {
	ArrayList<UIListener> listeners = new ArrayList<>();
	Stage window;
	Scene scene;
	GridPane gp;
	Label testIndexLabel;
	TextField testIndexText;
	Button displayAllTestsButton, chooseTestButton;
	boolean res;
	int testIndex;

	public CloneTestView(Stage stage) {
		window = stage;
		window.setTitle("Clone Test");
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

		GridPane.setConstraints(testIndexLabel, 0, 0);
		GridPane.setConstraints(testIndexText, 1, 0);
		GridPane.setConstraints(chooseTestButton, 2, 0);
		GridPane.setConstraints(displayAllTestsButton, 0, 3);

		GridPane.setHalignment(testIndexLabel, HPos.RIGHT);
		GridPane.setValignment(testIndexLabel, VPos.CENTER);
		GridPane.setHalignment(testIndexText, HPos.CENTER);
		GridPane.setValignment(testIndexText, VPos.CENTER);
		GridPane.setHalignment(chooseTestButton, HPos.CENTER);
		GridPane.setValignment(chooseTestButton, VPos.CENTER);
		GridPane.setHalignment(displayAllTestsButton, HPos.CENTER);
		GridPane.setValignment(displayAllTestsButton, VPos.CENTER);

		gp.getChildren().addAll(testIndexLabel, testIndexText, displayAllTestsButton, chooseTestButton);

		gp.setAlignment(Pos.CENTER);

		gp.setHgap(5);
		gp.setVgap(10);

	}

	public void buttons() {

		testIndexLabel = new Label("Test index):");

		testIndexText = new TextField();

		displayAllTestsButton = new Button("Display all tests");
		chooseTestButton = new Button("Choose test");

		chooseTestButton.setOnAction(e -> {
			try {
				testIndex = Integer.parseInt(testIndexText.getText());
				System.out.println(testIndex);
				if (testIndex > 0 && testIndex <= getNumOfTests()) {
					cloneTest(--testIndex);
					finish();
				} else if (getNumOfTests() == 0) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error!");
					alert.setHeaderText("There are no tests in the database!!");
					alert.setContentText("Please create more tests before trying to clone one!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});

					testIndexText.clear();

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
					testIndexText.clear();
				}
			} catch (Exception exception) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error!");
				alert.setHeaderText("Wrong input");
				alert.setContentText("Please enter a size that is in range of tests index!");
				alert.showAndWait().ifPresent(rs -> {
					if (rs == ButtonType.OK) {
						System.out.println("Pressed OK.");
					}
				});
				testIndexText.clear();

			}

		});

		displayAllTestsButton.setOnAction(e -> {
			OpenAllTestsView();
		});

		displayAllTestsButton.setMinWidth(150);
		chooseTestButton.setMinWidth(120);

	}

	public void cloneTest(int i) {
		for (UIListener l : listeners) {
			l.cloneTest(i);
		}
	}

	private int getNumOfTests() {
		int num = 0;
		for (UIListener l : listeners) {
			num = l.getNumOfTests();
		}
		return num;
	}

	@Override
	public void finish() throws FileNotFoundException {
		for (UIListener l : listeners) {
			l.finishTest();
		}
	}

	@Override
	public void OpenAllTestsView() {
		for (UIListener l : listeners) {
			l.OpenAllTestsView(new Stage());
		}
	}

}
