package view;

import java.io.FileNotFoundException;

import listeners.UIListener;

public interface AbstractCreateTestRandomlyView {

	void registerListener(UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	boolean isQuestionValid(int questIndex);

	void createTest(int size);

	void finish() throws FileNotFoundException;

	int getNumOfValidQuestions();

	int getNumOfQuestions();

	int randomQuestIndex();

	boolean addQuestionToTest(int questIndex) throws Exception;

	void OpenAllValidQuestionView();

}
