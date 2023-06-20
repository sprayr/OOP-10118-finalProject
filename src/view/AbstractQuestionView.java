package view;

import listeners.UIListener;

public interface AbstractQuestionView {

	void registerListener(UIListener listener);

	void scene();

	void textArea();

	void insertAllQuestionsToUI(String string);

	void closeWindow();

}
