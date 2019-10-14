
import java.io.File;
import java.util.ArrayList;


public class Model {


	// all views of this model
	private ArrayList<IView> views = new ArrayList<>();
	public ArrayList<File> files = new ArrayList<>();
	private MainView mainView;
	private IView layoutView;
	private IView barView;

	// set the view observer
	public void addLayoutView(IView view) {
		layoutView = view;
	}

	public void addMainView(MainView view) {
		mainView = view;
	}

	public void addBarView(IView view) {
		barView = view;
	}

	public void addFile(File[] images) {
		for (File file: images) {
			if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
				files.add(file);
			} else {
				System.err.println("This file type is not supported.");
			}
		}
		layoutView.updateView();
	}
	public void expandView() {
		mainView.expandView();
	}

	// notify the IView observer
	private void notifyObservers() {
		System.out.println("Model: notify View");
		for (IView view : this.views) {
			view.updateView();
		}
	}
}
