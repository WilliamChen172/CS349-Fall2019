import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

class LayoutView extends JPanel {

	private Model 				 model;
	private ArrayList<ImageView> imageList;
	private int 				 columns = 3;
	private boolean 			 isGrid;

	LayoutView(Model model) {
		imageList = new ArrayList<>();
		this.setPreferredSize(new Dimension(500, 550));
		this.setLayout(null);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEmptyBorder());

		// set the model
		this.model = model;
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				int preferredColumns;
				if (isGrid) {
					if (getWidth() > 1050) {
						preferredColumns = 3;
					} else if (getWidth() > 700) {
						preferredColumns = 2;
					} else {
						preferredColumns = 1;
					}

					if (preferredColumns != columns) {
						columns = preferredColumns;
						updateGridColumns();
					}
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

	void updateView(boolean isGridView, int rating) {
		imageList.clear();
		isGrid = isGridView;
		for (File file: model.files) {
			if (model.ratings.get(model.files.indexOf(file)) >= rating) {
				ImageView imageView = new ImageView(file, model, isGridView, model.ratings.get(model.files.indexOf(file)));
				imageList.add(imageView);
			}
		}
		if (isGridView) {
			updateGridColumns();
		} else {
			updateListColumns();
		}
	}

	private void updateGridColumns() {
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

		for (ImageView imageView: imageList) {
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

	private void updateListColumns() {
		int leftInset = 50;
		int topInset = 25;
		int imageInset = 25;
		Insets insets = this.getInsets();

		this.removeAll();
		this.repaint();

		this.setPreferredSize(new Dimension(500, 250*(imageList.size())));

		for (ImageView imageView: imageList) {
			Dimension size = imageView.getPreferredSize();
			int viewWidth = size.width;
			int viewHeight = size.height;
			this.add(imageView);

			imageView.setBounds(leftInset + insets.left, topInset + insets.top, size.width, size.height);
			JPanel divider = new JPanel();
			this.add(divider);
			divider.setBounds(305, topInset - imageInset, 1500, 1);
			divider.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
			topInset += viewHeight + imageInset*2;
		}
		this.revalidate();
	}
}
