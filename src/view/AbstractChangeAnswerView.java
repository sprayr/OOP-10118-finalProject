package view;

import listeners.UIListener;

public interface AbstractChangeAnswerView {
	void registerListener(UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void OpenAllQuestionView();

	boolean isOpenQuestion(int i);

	boolean changeAnswerToModel(int questIndex, int answerIndex, String answer, boolean isTrue) throws Exception;

	int getNumOfQuestions();

	int getNumOfAnswersForQuestion(int questIndex);

}
