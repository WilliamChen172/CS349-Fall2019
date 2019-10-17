import javax.swing.*;
import java.awt.*;

class MainView extends JPanel{

	private JScrollPane mainPanel;
	private BarView     barView;
	private LayoutView  layoutView;
	private Model       model;

	private boolean     isGrid;

	MainView(Model model) {
		// set layout for the whole panel
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1120, 1500));

		// set the model
		this.model = model;
		this.isGrid = true;

		// create LayoutView
		layoutView = new LayoutView(model);
		model.addLayoutView(layoutView);

		// create BarView
		barView = new BarView(model);
		model.addBarView(barView);

		// setup mainView
		this.add(barView, BorderLayout.NORTH);
		mainPanel = new JScrollPane(layoutView);
		mainPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		mainPanel.getVerticalScrollBar().setUnitIncrement(12);
		this.add(mainPanel, BorderLayout.CENTER);

	}


}
