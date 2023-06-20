package view;

import java.io.FileNotFoundException;

import listeners.UIListener;

public interface AbstractCreateTestManuallyView {

	void registerListener(UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void OpenAllValidQuestionView();

	boolean isQuestionValid(int questIndex);

	void createTest(int size);

	void finish() throws FileNotFoundException;

	int getNumOfValidQuestions();

	int getNumOfQuestions();

	 boolean addQuestionToTest(int questIndex);

}
