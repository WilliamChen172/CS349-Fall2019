// HelloMVC: a simple MVC example
// the model is just a counter
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

class ListView extends JPanel implements IView {

	// the model that this view is showing
	private Model model;
	private ArrayList<GridImageView> imageList;

	public ListView(Model model) {
		imageList = new ArrayList<>();
		// a GridBagLayout with default constraints centres
		// the widget in the window
		this.setLayout(new GridLayout(0, 3));

		// set the model
		this.model = model;
	}

	// IView interface
	public void updateView() {
		this.removeAll();
		for (File file: model.files) {
			GridImageView imageView = new GridImageView(file);
			imageList.add(imageView);
		}
		for (GridImageView imageView: imageList) {
			this.add(imageView);
		}
		System.out.println("ListView number: " + imageList.size());
	}
}
