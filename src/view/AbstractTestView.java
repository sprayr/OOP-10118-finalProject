package view;

import listeners.UIListener;

public interface AbstractTestView {
	void registerListener(UIListener listener);

	void scene();

	void textArea();

	void insertAllQuestionsToUI(String string);

	void closeWindow();
}
