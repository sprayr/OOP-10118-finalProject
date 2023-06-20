package listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.stage.Stage;
import model.Question;

public interface UIListener {

	void OpenAllQuestionView(Stage window);

	void OpenAddQuestionView(Stage window);

	void OpenChangeQuestionView(Stage window);

	boolean addQuestionToModel(Question question);

	void openAddAnswerView(Stage stage);

	int getNumOfQuestions();

	boolean isOpenQuestion(int i);

	boolean addAnswerToModel(int index, String text, boolean b) throws Exception;

	boolean changeQuestion(int index, String str);

	void openChanegeAnswerView(Stage stage);

	boolean changeAnswerToModel(int questIndex, int answerIndex, String answer, boolean isTrue);

	int getNumOfAnswersForQuestion(int questIndex);

	boolean deleteAnswer(int questIndex, int answerIndex);

	void openDeleteAnswerView(Stage stage);

	void OpenAllValidQuestionView(Stage window);

	boolean isValidQuestion(int questIndex);

	void finishTest() throws FileNotFoundException;

	void createTest(int size);

	int getNumOfValidQuestions();

	void openCreateTestManuallyView(Stage stage);

	boolean addQuestionToTest(int questIndex);

	boolean addQuestionToTestRandomly(int questIndex) throws Exception;

	void openCreateTestRandomlyView(Stage stage);

	void OpenAllTestsView(Stage stage);

	int getNumOfTests();

	void openCloneTestView(Stage stage);

	void cloneTest(int i);

	void saveAndExit() throws FileNotFoundException, IOException;

}
