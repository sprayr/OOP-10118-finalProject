package view;

import listeners.UIListener;

public interface AbstractChangeQuestionView {

	void registerListener(UIListener listener);

	void scene();

	void gridPane();

	void buttons();

	void OpenAllQuestionView();

	int getNumOfQuestions();

	boolean changeQuestion(int index, String str);
}
