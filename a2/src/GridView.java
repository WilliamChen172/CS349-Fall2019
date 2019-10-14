// HelloMVC: a simple MVC example
// the model is just a counter
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

class GridView extends JPanel implements IView {

	// the model that this view is showing
	private Model model;
	private ArrayList<GridImageView> imageList;
	private int columns = 3;


	public GridView(Model model) {
		this.setBorder(BorderFactory.createLineBorder(Color.red));
		imageList = new ArrayList<>();
		// a GridBagLayout with default constraints centres
		// the widget in the window
		this.setPreferredSize(new Dimension(500, 950));
		this.setLayout(null);

		// set the model
		this.model = model;
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				int preferredColumns;
				if (getWidth() > 1050) {
					preferredColumns = 3;
				} else if (getWidth() > 700) {
					preferredColumns = 2;
				} else {
					preferredColumns = 1;
				}

				if (preferredColumns != columns) {
					columns = preferredColumns;
					updateColumns();
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	// IView interface
	public void updateView() {
		imageList.clear();

		for (File file: model.files) {
			GridImageView imageView = new GridImageView(file);
			imageList.add(imageView);
		}

		updateColumns();
	}

	public void updateColumns() {
		int leftInset = 50;
		int topInset = 50;
		int imageInset = 50;
		int nextRow = 0;
		int totalRows;
		Insets insets = this.getInsets();

		this.removeAll();
		this.repaint();

		if (imageList.size() % columns > 0) {
			totalRows = imageList.size() / columns + 1;
		} else {
			totalRows = imageList.size() / columns;
		}

		this.setPreferredSize(new Dimension(500, imageInset*(totalRows+1) + 400*(totalRows)));

		for (GridImageView imageView: imageList) {
			Dimension size = imageView.getPreferredSize();
			int viewWidth = size.width;
			int viewHeight = size.height;
			this.add(imageView);

			imageView.setBounds(leftInset + insets.left, topInset + insets.top, size.width, size.height);
			nextRow++;

			if (nextRow < columns) {
				leftInset += viewWidth + imageInset;
			} else {
				leftInset = imageInset;
				topInset += viewHeight + imageInset;
				nextRow = 0;
			}

		}
		this.revalidate();
	}
}
