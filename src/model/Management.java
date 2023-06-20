package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import controller.Controller;
import listeners.BLListener;

public class Management implements Serializable {

	private ArrayList<BLListener> listeners = new ArrayList<>();
	private static final long serialVersionUID = 1852490822425257908L; // auto-generated by Serializable
	private ArrayList<Question> allQuestions; // question database
	private Test test;
	private ArrayList<Test> allTests;
	private int numOfQuestions; // questions counter

	public Management() throws FileNotFoundException, ClassNotFoundException, IOException {
		this.allQuestions = new ArrayList<Question>();
		numOfQuestions = 0;
		test = null;
		allTests = new ArrayList<Test>();
		initialize();
	}

	public ArrayList<Question> getAllQuestions() {
		return allQuestions;
	}

	public boolean isOpenQuestion(int questIndex) {
		return allQuestions.get(questIndex).getClass().equals(OpenQuestion.class);
	}

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public void cloneATest(int index) {
		test = allTests.get(index);
	}

	public void createTest(int size) {
		test = new Test(size);
	}

	/* change question string */
	public boolean changeQuestion(int questIndex, String quest) throws IndexOutOfBoundsException {
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions.get(i).getClass().equals(allQuestions.get(questIndex).getClass())
					&& allQuestions.get(i).getQuest().equals(quest) && questIndex != i) {
				return false;
			}
		}
		if (allQuestions.get(questIndex).setQuest(quest)) {
			fireChangedQuestion();
			return true;
		}
		return false;
	}

	private void fireChangedQuestion() {
		for (BLListener l : listeners) {
			l.changedQuestion();
		}
	}

	/* change the answer of any question */
	public boolean changeAnswer(int questIndex, int answerIndex, String answer, boolean isTrue)
			throws IndexOutOfBoundsException {
		/* if the answerIndex is negative - its an openQuestion */
		if (answerIndex < 0) {
			if (((OpenQuestion) allQuestions.get(questIndex)).setAnswer(answer)) {
				fireChangedAnswer();
				return true;
			} else {
				return false;
			}
		} /* else its a multi choice question */
		MySet<Answer> answers = ((MultiChoiceQuestion) allQuestions.get(questIndex)).getAnswers(); //
		for (int i = 0; i < ((MultiChoiceQuestion) allQuestions.get(questIndex)).getNumOfAnswers(); i++) {
			if (answers.get(i).getAnswer().equals(answer)) {
				return false;
			}
		}
		if (((MultiChoiceQuestion) allQuestions.get(questIndex)).changeAnswer(answer, isTrue, answerIndex)) {

			fireChangedAnswer();
			return true;
		} else {

			return false;
		}

	}

	private void fireChangedAnswer() {
		for (BLListener l : listeners) {
			l.ChangedAnswer();
		}
	}

	/* add answer to existing question */
	public boolean addAnswer(int questIndex, String answer, boolean isTrue) throws Exception {
		if (allQuestions.get(questIndex).getClass().equals(OpenQuestion.class)) {
			if (((OpenQuestion) allQuestions.get(questIndex)).addAnswer(answer, true)) {
				fireAddedAnswer();
				return true;
			}
			;
		}
		if (((MultiChoiceQuestion) allQuestions.get(questIndex)).addAnswer(answer, isTrue)) {
			fireAddedAnswer();
			return true;
		}
		return false;

	}

	public void fireAddedAnswer() {
		for (BLListener l : listeners) {
			l.AnswerAdded();
		}
	}

	/* removes an answer for a question by index */
	public boolean removeAnswer(int questIndex, int answerIndex) throws IndexOutOfBoundsException {

		if (allQuestions.get(questIndex).removeAnswer(answerIndex)) {
			fireDeletedAnswer();
			return true;
		} else {
			return false;
		}

	}

	private void fireDeletedAnswer() {
		for (BLListener l : listeners) {
			l.deletedAnswer();
		}
	}

	public int numOfValidQuestions() throws IndexOutOfBoundsException {
		int counter = 0;
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions.get(i).isValid()) {
				counter++;
			}
		}
		return counter;
	}

	/* print all questions in the array */
	public void printAllQuestions() throws IndexOutOfBoundsException {
		System.out.println(toString());
		fireDisplayAllQuestions();
	}

	private void fireDisplayAllQuestions() {
		for (BLListener l : listeners) {
			l.gotAllQuestions(toString());
		}
	}

	public void printAllValidQuestions() throws IndexOutOfBoundsException {
		StringBuffer sb = new StringBuffer();
		sb.append("\nThe valid Questions are: \n\n");
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions.get(i).isValid()) {
				sb.append(
						"[" + (i + 1) + "] " + allQuestions.get(i).toString() + "\n ------------------------------ \n");
			}
		}
		System.out.println(sb);
		fireDisplayAllValidQuestions(sb.toString());
	}

	private void fireDisplayAllValidQuestions(String string) {
		for (BLListener l : listeners) {
			l.gotAllValidQuestions(string);
		}
	}

	/* add a new question */
	public boolean addQuestion(Question temp) throws IndexOutOfBoundsException {
		if (temp.getClass() == OpenQuestion.class) {
			temp = (OpenQuestion) temp;
		} else {
			temp = (MultiChoiceQuestion) temp;
		}
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions.get(i).equals(temp))
				return false;
		}
		allQuestions.add(temp);
		numOfQuestions++;
		fireAddedQuestion(temp);
		return true;
	}

	private void fireAddedQuestion(Question temp) {
		for (BLListener l : listeners) {
			l.QuestionAdded(temp);
		}
	}

	public boolean validQuestion(int questIndex) throws IndexOutOfBoundsException {
		return allQuestions.get(questIndex).isValid();
	}

	public boolean addQuestionToTestByIndex(int questIndex) {
		return test.addQuestion(allQuestions.get(questIndex));
	}

	/*
	 * if the question is multi choice Question the method makes a copy of it and
	 * then randomly picks the answers
	 */
	public boolean addQuestionToTestRandomly(int questIndex) throws Exception {
		if (allQuestions.get(questIndex).getClass().equals(MultiChoiceQuestion.class)) {
			MultiChoiceQuestion temp = new MultiChoiceQuestion((MultiChoiceQuestion) allQuestions.get(questIndex));
			temp.pickRandomAnswers();
			return test.addQuestion(temp);
		}
		return test.addQuestion(allQuestions.get(questIndex));
	}

	public ArrayList<Test> getAllTests() {
		return allTests;
	}

	public void sortTest() throws IndexOutOfBoundsException {
		test.sortQuestions();
	}

	public void printTestWithoutAnswers() throws IndexOutOfBoundsException {
		System.out.println("The test without the answers:\n\n" + test.toString());
		firePrintTestWithoutAnswers("The test without the answers:\n\n" + test.toString());
	}

	private void firePrintTestWithoutAnswers(String string) {
		for (BLListener l : listeners) {
			l.printTestWithoutAnswers(string);
		}
	}

	public void printTestWithAnswers() throws IndexOutOfBoundsException {
		System.out.println("The test with the answers:\n\n" + test.toStringWithAnswer());
		firePrintTestWithAnswers("The test with the answers:\n\n" + test.toStringWithAnswer());
	}

	private void firePrintTestWithAnswers(String stringWithAnswer) {
		for (BLListener l : listeners) {
			l.printTestWithAnswers(stringWithAnswer);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Management other = (Management) obj;
		return Objects.equals(allQuestions, other.allQuestions);
	}

	public String toString() throws IndexOutOfBoundsException {
		StringBuffer sb = new StringBuffer();
		if (numOfQuestions == 0) {
			sb.append("\nThere are no questions!\n");
		} else {
			sb.append("\nThe Questions are: \n\n");
			for (int i = 0; i < numOfQuestions; i++) {
				sb.append((i + 1) + ". " + allQuestions.get(i).toString() + "\n ------------------------------ \n");
			}

		}
		return sb.toString();
	}

	public void addTestToAllTests() {
		allTests.add(test);
	}

	/* saves test to text file! */
	public void saveTest() throws FileNotFoundException {
		int i = 1;
		File f1 = new File("exam_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")));
		File f2 = new File("solution_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")));
		while (f1.exists()) {
			f1 = new File(
					"exam_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + "(" + i + ")");
			f2 = new File("solution_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + "(" + i
					+ ")");
			i++;
		}
		PrintWriter pw1 = new PrintWriter(f1);
		PrintWriter pw2 = new PrintWriter(f2);
		test.save(pw1);
		test.saveWithAnswers(pw2);
		pw1.close();
		pw2.close();
	}

	/* prints all tests in the array */
	public void printAllTests() {
		StringBuffer sb = new StringBuffer();
		if (allTests.size() == 0) {
			System.out.println("There are no tests!\n");
			sb.append("There are no tests!\n");
		} else {
			for (int i = 0; i < allTests.size(); i++) {
				System.out.println("\n -------------------------------------Test[" + (i + 1)
						+ "]-------------------------------------\n" + allTests.get(i).toStringWithAnswer()
						+ "\n\n _____________________________________________________________________________________\n");
				sb.append("\n -------------------------------------Test[" + (i + 1)
						+ "]-------------------------------------\n" + allTests.get(i).toStringWithAnswer()
						+ "\n\n ___________________________________________________________________\n");
			}
		}
		firePrintAllTests(sb.toString());
	}

	private void firePrintAllTests(String string) {
		for (BLListener l : listeners) {
			l.printAllTests(string);
		}

	}

	/*
	 * called at the start of the program - reads questions and tests from binary
	 * file
	 */
	public void initialize() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream("questions.txt"));
		allQuestions = (ArrayList<Question>) objectInput.readObject();
		numOfQuestions = allQuestions.size();
		objectInput = new ObjectInputStream(new FileInputStream("tests.txt"));
		allTests = (ArrayList<Test>) objectInput.readObject();
		objectInput.close();

	}

	/* saves all questions to binary file! */
	public void saveAllQuestions() throws FileNotFoundException, IOException {
		ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream("questions.txt"));
		objectOutput.writeObject(allQuestions);
		objectOutput.close();
	}

	/* saves all tests to binary file! */
	public void saveAllTests() throws FileNotFoundException, IOException {
		ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream("tests.txt"));
		objectOutput.writeObject(allTests);
		objectOutput.close();
	}

	public void registerListener(BLListener listener) {
		listeners.add(listener);
	}

	public void unregisterListener(BLListener listener) {
		listeners.remove(listener);

	}

	// the method is called only for multichoice questions!
	public int getNumOfAnswersForQuestion(int questIndex) {
		return ((MultiChoiceQuestion) allQuestions.get(questIndex)).getNumOfAnswers();
	}

	// after all questions were added to test!
	public void finishTest() throws FileNotFoundException {
		System.out.println("finished");
		sortTest();
		saveTest();
		addTestToAllTests();
		printTestWithAnswers();
		printTestWithoutAnswers();

	}

}
