import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainView extends JPanel implements IView {
	private JScrollPane mainPanel;
	private BarView barView;
	private GridView gridView;
	private ListView listView;
	private Model model;

	private boolean isGrid;

	public MainView(Model model) {
		// set layout for the whole panel
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1120, 1500));
		// set the model
		this.model = model;

		// create GridView, tell it about model
		gridView = new GridView(model);
		model.addLayoutView(gridView);

		// create ListView
		listView = new ListView(model);
		//model.addView(listView);

		// create BarView
		barView = new BarView(model);
		model.addBarView(barView);

		// setup mainView
		this.add(barView, BorderLayout.NORTH);
		mainPanel = new JScrollPane(gridView);
		mainPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(mainPanel, BorderLayout.CENTER);
		isGrid = true;
	}

	// IView interface
	public void updateView() {
		System.out.println("Layout isGrid: " + isGrid);
		if (isGrid) {
			this.remove(gridView);
			this.add(listView, BorderLayout.CENTER);
			isGrid = false;
		} else {
			this.remove(gridView);
			this.add(gridView, BorderLayout.CENTER);
			isGrid = true;
		}
	}

	public void expandView() {
		//mainPanel.setBounds(0, 0, mainPanel.getWidth(), mainPanel.getHeight() + 400);
		//mainPanel.resize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()+400));
		mainPanel.revalidate();
		mainPanel.repaint();
	}

}
