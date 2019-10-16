import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BarView extends JPanel {

	private JButton gridButton;
	private JButton listButton;
	private JLabel       nameLabel;
	private JButton 	 importButton;
	private JLabel  	 filterLabel;
	private JButton 	 filterButton;
	private JFileChooser fileChooser;

	private Model model;

	public BarView(Model model) {
		// set layout for the whole panel
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(1150, 50));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		// create the layout toggle buttons
		ImageIcon gridIcon = new ImageIcon("./src/UI/grid_icon.png");
		ImageIcon listIcon = new ImageIcon("./src/UI/list_icon.png");
		gridButton = new JButton(gridIcon);
		listButton = new JButton(listIcon);
		gridButton.setBorder(BorderFactory.createLoweredBevelBorder());
		listButton.setBorder(BorderFactory.createRaisedBevelBorder());
		gridButton.setMaximumSize(new Dimension(50, 40));
		listButton.setMaximumSize(new Dimension(50, 40));
		gridButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!model.isGridView) {
					gridButton.setBorder(BorderFactory.createLoweredBevelBorder());
					listButton.setBorder(BorderFactory.createRaisedBevelBorder());
					model.switchLayout();
				}

			}
		});
		listButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.isGridView) {
					listButton.setBorder(BorderFactory.createLoweredBevelBorder());
					gridButton.setBorder(BorderFactory.createRaisedBevelBorder());
					model.switchLayout();
				}

			}
		});

		// create the display name of the application
		nameLabel = new JLabel("Fotag");

		// create the import button
		importButton = new JButton("import");
		fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(BarView.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File[] files = fileChooser.getSelectedFiles();
					model.addFile(files);
				} else {
				}
			}
		});
		// create the filter part
		filterLabel = new JLabel("Filter by: ");
		filterButton = new JButton("filter");

		// add every component to the panel
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(gridButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(listButton);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		this.add(nameLabel);
		this.add(Box.createHorizontalGlue());
		this.add(importButton);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		this.add(filterLabel);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(filterButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));

		// create the view UI
//		gridButton.setMaximumSize(new Dimension(100, 100));
//		gridButton.setPreferredSize(new Dimension(100, 50));

		// set the model
		this.model = model;

	}
}
