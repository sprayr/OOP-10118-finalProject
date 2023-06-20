package view;

import listeners.UIListener;
import model.Question;

public interface AbstractAddQuestionView {

	void registerListener(UIListener listener);

	void scene();

	void gridPane();

	void buttons();

	boolean addQuestionToController(Question quest);

	void openAddAnswerView();

	void OpenAllQuestionView();
}
