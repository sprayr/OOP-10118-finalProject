package view;

import java.io.FileNotFoundException;

import listeners.UIListener;

public interface AbstractCloneTestView {
	void registerListener(UIListener listener);

	void buttons();

	void scene();

	void gridPane();

	void cloneTest(int i);

	void finish() throws FileNotFoundException;

	void OpenAllTestsView();

}
