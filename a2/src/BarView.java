import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class BarView extends JPanel {

	private JButton gridButton;
	private JButton listButton;
	private JLabel       nameLabel;
	private JButton 	 importButton;
	private JLabel  	 filterLabel;
	private ArrayList<JButton> 	 filter;
	private JFileChooser fileChooser;
	private JButton clearButton;

	private Model model;

	private ImageIcon gridOnIcon = new ImageIcon("./src/UI/grid_on.png");
	private ImageIcon gridOffIcon = new ImageIcon("./src/UI/grid_off.png");
	private ImageIcon listOnIcon = new ImageIcon("./src/UI/list_on.png");
	private ImageIcon listOffIcon = new ImageIcon("./src/UI/list_off.png");
	private ImageIcon importIcon = new ImageIcon("./src/UI/import.png");
	private ImageIcon clearIcon = new ImageIcon("./src/UI/clear.png");
	private ImageIcon emptyStarIcon = new ImageIcon("./src/UI/star_empty.png");
	private ImageIcon fullStarIcon = new ImageIcon("./src/UI/star_full.png");


	public BarView(Model model) {
		// set layout for the whole panel
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(1150, 50));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		// create the layout toggle buttons
		gridButton = new JButton(gridOnIcon);
		listButton = new JButton(listOffIcon);
		gridButton.setMaximumSize(new Dimension(50, 40));
		listButton.setMaximumSize(new Dimension(50, 40));
		gridButton.setBorder(BorderFactory.createEmptyBorder());
		listButton.setBorder(BorderFactory.createEmptyBorder());
		gridButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!model.isGridView) {
					gridButton.setIcon(gridOnIcon);
					listButton.setIcon(listOffIcon);
					model.switchLayout();
				}

			}
		});
		listButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.isGridView) {
					listButton.setIcon(listOnIcon);
					gridButton.setIcon(gridOffIcon);
					model.switchLayout();
				}

			}
		});

		// create the display name of the application
		nameLabel = new JLabel("Fotag");

		// create the import button
		importButton = new JButton(importIcon);
		importButton.setBorder(BorderFactory.createEmptyBorder());
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
		filter = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			JButton star = new JButton(emptyStarIcon);
			star.setBorder(BorderFactory.createEmptyBorder());
			filter.add(star);
			int rating = i+1;
			star.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int rating = filter.indexOf(star) + 1;
					model.filter(rating);
					for (int i = 0; i < rating; i++) {
						filter.get(i).setIcon(fullStarIcon);
					}
					for (int j = rating; j < filter.size(); j++) {
						filter.get(j).setIcon(emptyStarIcon);
					}
				}
			});
		}

		// create clear button
		clearButton = new JButton(clearIcon);
		clearButton.setBorder(BorderFactory.createEmptyBorder());
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.files.clear();
				model.updateLayout();
			}
		});

		// add every component to the panel
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(gridButton);
		//this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(listButton);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		this.add(nameLabel);
		this.add(Box.createHorizontalGlue());
		this.add(importButton);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		this.add(filterLabel);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		for (JButton star: filter) {
			this.add(star);
			//this.add(Box.createRigidArea(new Dimension(5, 0)));
		}
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(clearButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));

		// create the view UI
//		gridButton.setMaximumSize(new Dimension(100, 100));
//		gridButton.setPreferredSize(new Dimension(100, 50));

		// set the model
		this.model = model;

	}
}
