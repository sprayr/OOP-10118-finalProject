package controller;

import java.awt.Window;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.stage.Stage;
import listeners.*;
import model.*;
import view.AbstractAddAnswerView;
import view.AbstractAddQuestionView;
import view.AbstractChangeAnswerView;
import view.AbstractChangeQuestionView;
import view.AbstractCloneTestView;
import view.AbstractCreateTestManuallyView;
import view.AbstractCreateTestRandomlyView;
import view.AbstractDeleteAnswerView;
import view.AbstractQuestionView;
import view.AbstractTestView;
import view.AbstractView;
import view.AddAnswerView;
import view.AddQuestionView;
import view.AllQuestionsView;
import view.AllValidQuestionsView;
import view.ChangeAnswerView;
import view.ChangeQuestionView;
import view.CloneTestView;
import view.CreateTestManuallyView;
import view.CreateTestRandomlyView;
import view.DeleteAnswerView;
import view.TestView;

public class Controller implements BLListener, UIListener {

	private Management model;
	private AbstractView primaryView;
	private AbstractQuestionView questView;
	private AbstractAddQuestionView addQuestView;
	private AbstractAddAnswerView addAnswerView;
	private AbstractChangeQuestionView changeQuestionView;
	private AbstractChangeAnswerView changeAnswerView;
	private AbstractDeleteAnswerView deleteAnswerView;
	private AbstractQuestionView allValidQuetionView;
	private AbstractCreateTestManuallyView createTestManuallyView;
	private AbstractCreateTestRandomlyView createTestRandomlyView;
	private AbstractTestView testWithAnswersView, testWithoutAnswersView, allTestsView;
	private AbstractCloneTestView cloneTestView;

	public Controller(Management model, AbstractView primaryView) {
		this.model = model;
		this.primaryView = primaryView;

		model.registerListener(this);
		primaryView.registerListener(this);
	}

	@Override
	public void OpenAllQuestionView(Stage window) {
		questView = new AllQuestionsView(window);
		questView.registerListener(this);
		model.printAllQuestions();

	}

	@Override
	public void gotAllQuestions(String string) {
		questView.insertAllQuestionsToUI(string);
	}

	@Override
	public void OpenAddQuestionView(Stage window) {
		addQuestView = new AddQuestionView(window);
		addQuestView.registerListener(this);

	}

	@Override
	public boolean addQuestionToModel(Question question) {
		return model.addQuestion(question);

	}

	@Override
	public void QuestionAdded(Question question) {
		this.questView.closeWindow();
		OpenAllQuestionView(new Stage());
	}

	@Override
	public void openAddAnswerView(Stage stage) {
		addAnswerView = new AddAnswerView(stage);
		addAnswerView.registerListener(this);
	}

	@Override
	public int getNumOfQuestions() {
		return model.getNumOfQuestions();
	}

	public boolean isOpenQuestion(int i) {
		return model.isOpenQuestion(i);
	}

	@Override
	public boolean addAnswerToModel(int index, String text, boolean b) throws Exception {
		return model.addAnswer(index, text, b);

	}

	@Override
	public void AnswerAdded() {
		questView.closeWindow();
		OpenAllQuestionView(new Stage());
	}

	@Override
	public boolean changeQuestion(int index, String str) {
		return model.changeQuestion(index, str);

	}

	@Override
	public void changedQuestion() {
		questView.closeWindow();
		OpenAllQuestionView(new Stage());
	}

	@Override
	public void OpenChangeQuestionView(Stage window) {
		changeQuestionView = new ChangeQuestionView(window);
		changeQuestionView.registerListener(this);

	}

	@Override
	public void openChanegeAnswerView(Stage stage) {
		changeAnswerView = new ChangeAnswerView(stage);
		changeAnswerView.registerListener(this);
	}

	@Override
	public boolean changeAnswerToModel(int questIndex, int answerIndex, String answer, boolean isTrue) {
		return model.changeAnswer(questIndex, answerIndex, answer, isTrue);
	}

	@Override
	public int getNumOfAnswersForQuestion(int questIndex) {
		return model.getNumOfAnswersForQuestion(questIndex);
	}

	@Override
	public void ChangedAnswer() {
		questView.closeWindow();
		OpenAllQuestionView(new Stage());

	}

	@Override
	public boolean deleteAnswer(int questIndex, int answerIndex) {
		return model.removeAnswer(questIndex, answerIndex);
	}

	@Override
	public void openDeleteAnswerView(Stage stage) {
		deleteAnswerView = new DeleteAnswerView(stage);
		deleteAnswerView.registerListener(this);
	}

	@Override
	public void deletedAnswer() {
		questView.closeWindow();
		OpenAllQuestionView(new Stage());
	}

	@Override
	public void OpenAllValidQuestionView(Stage window) {
		allValidQuetionView = new AllValidQuestionsView(window);
		allValidQuetionView.registerListener(this);
		model.printAllValidQuestions();

	}

	@Override
	public void gotAllValidQuestions(String string) {
		allValidQuetionView.insertAllQuestionsToUI(string);
	}

	@Override
	public boolean isValidQuestion(int questIndex) {
		return model.validQuestion(questIndex);
	}

	@Override
	public void finishTest() throws FileNotFoundException {

		model.finishTest();
	}

	@Override
	public void createTest(int size) {
		model.createTest(size);

	}

	@Override
	public int getNumOfValidQuestions() {
		return model.numOfValidQuestions();
	}

	@Override
	public void openCreateTestManuallyView(Stage stage) {
		createTestManuallyView = new CreateTestManuallyView(stage);
		createTestManuallyView.registerListener(this);
	}

	@Override
	public boolean addQuestionToTest(int questIndex) {
		return model.addQuestionToTestByIndex(questIndex);
	}

	@Override
	public void printTestWithAnswers(String stringWithAnswer) {
		testWithAnswersView = new TestView(new Stage());
		testWithAnswersView.insertAllQuestionsToUI(stringWithAnswer);

	}

	@Override
	public void printTestWithoutAnswers(String string) {
		testWithoutAnswersView = new TestView(new Stage());
		testWithoutAnswersView.insertAllQuestionsToUI(string);

	}

	@Override
	public boolean addQuestionToTestRandomly(int questIndex) throws Exception {
		return model.addQuestionToTestRandomly(questIndex);

	}

	@Override
	public void openCreateTestRandomlyView(Stage stage) {
		createTestRandomlyView = new CreateTestRandomlyView(stage);
		createTestRandomlyView.registerListener(this);
	}

	@Override
	public void OpenAllTestsView(Stage stage) {
		allTestsView = new TestView(stage);
		allTestsView.registerListener(this);
		model.printAllTests();

	}

	@Override
	public void printAllTests(String string) {
		allTestsView.insertAllQuestionsToUI(string);
	}

	@Override
	public int getNumOfTests() {
		return model.getAllTests().size();
	}

	@Override
	public void openCloneTestView(Stage stage) {
		cloneTestView = new CloneTestView(stage);
		cloneTestView.registerListener(this);
	}

	@Override
	public void cloneTest(int i) {
		model.cloneATest(i);

	}

	@Override
	public void saveAndExit() throws FileNotFoundException, IOException {
		model.saveAllQuestions();
		model.saveAllTests();
		primaryView.closeWindow();

	}

}
