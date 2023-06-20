package view;

import listeners.*;
import model.Question;

public interface AbstractView {
	void registerListener(UIListener listener);

	void scene();

	void gridPane();

	void buttons();

	void openAddQuestionView();

	void openAllQuestionView();

	void openAddAnswerView();

	void openChangeQuestionView();

	void openChangeAnswerView();

	void closeWindow();

}
