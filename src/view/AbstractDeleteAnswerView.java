package view;

import listeners.UIListener;

public interface AbstractDeleteAnswerView {
	void registerListener(UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void OpenAllQuestionView();

	boolean isOpenQuestion(int i);

	int getNumOfQuestions();

	boolean deleteAnswerFromModel(int questIndex, int answerIndex) throws Exception;

	int getNumOfAnswersForQuestion(int questIndex);
}
