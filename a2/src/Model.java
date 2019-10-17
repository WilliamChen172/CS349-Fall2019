import javax.swing.*;
import java.io.File;
import java.util.ArrayList;


class Model {
	// all views of this model
	ArrayList<File> files = new ArrayList<>();
	ArrayList<Integer> ratings = new ArrayList<>();

	private MainView mainView;
	private LayoutView layoutView;
	private BarView barView;

	JFrame mainFrame;

	boolean isGridView;
	private int filterRating;

	// set the view observer
	void addLayoutView(LayoutView view) {
		layoutView = view;
		isGridView = true;
	}

	void addMainView(MainView view) {
		mainView = view;
	}

	void addBarView(BarView view) {
		barView = view;
	}

	void addMainFrame(JFrame frame) {
		mainFrame = frame;
	}

	void switchLayout() {
		isGridView = !isGridView;
		layoutView.updateView(isGridView, filterRating);
	}

	void updateLayout() {
		layoutView.updateView(isGridView, filterRating);
	}
	void addFile(File[] images) {
		for (File file: images) {
			if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
				files.add(file);
				ratings.add(0);
			} else {
				System.err.println("This file type is not supported.");
			}
		}
		layoutView.updateView(isGridView, filterRating);
	}

	void updateRating(File image, int rating) {
		if (files.contains(image)) {
			ratings.set(files.indexOf(image), rating);
		} else {
			System.err.println("This image does not exist");
		}
	}

	void filter(int rating) {
		filterRating = rating;
		layoutView.updateView(isGridView, filterRating);
	}
}
