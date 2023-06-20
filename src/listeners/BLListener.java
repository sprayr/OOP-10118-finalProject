package listeners;

import model.Question;

public interface BLListener {

	void gotAllQuestions(String string);

	void QuestionAdded(Question question);

	void AnswerAdded();

	void changedQuestion();

	void ChangedAnswer();

	void deletedAnswer();

	void gotAllValidQuestions(String string);

	void printTestWithAnswers(String stringWithAnswer);

	void printTestWithoutAnswers(String string);

	void printAllTests(String string);

}
