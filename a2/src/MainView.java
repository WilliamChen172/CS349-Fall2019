import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel{
	private JScrollPane mainPanel;
	private BarView barView;
	private LayoutView layoutView;
	private Model model;

	private boolean isGrid;

	public MainView(Model model) {
		// set layout for the whole panel
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1120, 1500));
		// set the model
		this.model = model;

		// create GridView, tell it about model
		layoutView = new LayoutView(model);
		model.addLayoutView(layoutView);

		// create BarView
		barView = new BarView(model);
		model.addBarView(barView);

		// setup mainView
		this.add(barView, BorderLayout.NORTH);
		mainPanel = new JScrollPane(layoutView);
		mainPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(mainPanel, BorderLayout.CENTER);
		isGrid = true;
	}

	public void expandView() {
		//mainPanel.setBounds(0, 0, mainPanel.getWidth(), mainPanel.getHeight() + 400);
		//mainPanel.resize(new Dimension(mainPanel.getWidth(), mainPanel.getHeight()+400));
		mainPanel.revalidate();
		mainPanel.repaint();
	}

}
