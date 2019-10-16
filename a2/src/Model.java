
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;


public class Model {


	// all views of this model
	//private ArrayList<IView> views = new ArrayList<>();
	public ArrayList<File> files = new ArrayList<>();
	public ArrayList<Integer> ratings = new ArrayList<>();

	private MainView mainView;
	private LayoutView layoutView;
	private BarView barView;

	public JFrame mainFrame;

	public boolean isGridView;
	private int filterRating;

	// set the view observer
	public void addLayoutView(LayoutView view) {
		layoutView = view;
		isGridView = true;
	}

	public void addMainView(MainView view) {
		mainView = view;
	}

	public void addBarView(BarView view) {
		barView = view;
	}

	public void addMainFrame(JFrame frame) {
		mainFrame = frame;
	}

	public void switchLayout() {
		isGridView = !isGridView;
		layoutView.updateView(isGridView, filterRating);
	}

	public void updateLayout() {
		layoutView.updateView(isGridView, filterRating);
	}
	public void addFile(File[] images) {
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

	public void updateRating(File image, int rating) {
		if (files.contains(image)) {
			ratings.set(files.indexOf(image), rating);
		} else {
			System.err.println("This image does not exist");
		}
	}

	public void filter(int rating) {
		filterRating = rating;
		layoutView.updateView(isGridView, filterRating);
	}
}
