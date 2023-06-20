package view;

import listeners.UIListener;

public interface AbstractAddAnswerView {

	void registerListener(UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void OpenAllQuestionView();

	boolean isOpenQuestion(int i);

	boolean addAnswerToModel(int index, String text, boolean b) throws Exception;

	int getNumOfQuestions();

	
}
