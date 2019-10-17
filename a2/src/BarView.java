import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

class BarView extends JPanel {

	private JButton 			 gridButton;
	private JButton 			 listButton;
	private JLabel       		 nameLabel;
	private JButton 	  		 importButton;
	private JButton              clearImageButton;
	private JLabel  	         filterLabel;
	private ArrayList<JButton> 	 filter;
	private JFileChooser 		 fileChooser;
	private JButton 			 clearFilterButton;

	private Model 				 model;

	private ImageIcon gridOnIcon =    new ImageIcon("./src/UI/grid_on.png");
	private ImageIcon gridOffIcon =   new ImageIcon("./src/UI/grid_off.png");
	private ImageIcon listOnIcon =    new ImageIcon("./src/UI/list_on.png");
	private ImageIcon listOffIcon =   new ImageIcon("./src/UI/list_off.png");
	private ImageIcon importIcon =    new ImageIcon("./src/UI/import.png");
	private ImageIcon clearIcon =     new ImageIcon("./src/UI/clear_image.png");
	private ImageIcon emptyStarIcon = new ImageIcon("./src/UI/star_empty.png");
	private ImageIcon fullStarIcon =  new ImageIcon("./src/UI/star_full.png");


	BarView(Model model) {
		// set layout for the whole panel
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(1150, 50));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		// set the model
		this.model = model;

		// create Grid and List toggle buttons
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
		nameLabel = new JLabel("Fotag!");
		nameLabel.setFont(new Font("SanSerif", Font.PLAIN, 27));

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
					System.err.println("Failed to open this file.");
				}
			}
		});

		// create the filter label and view
		filterLabel = new JLabel("Filter by:");
		filterLabel.setFont(new Font("SanSerif", Font.PLAIN, 21));
		filterLabel.setForeground(Color.darkGray);
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

		// create clear images button
		clearImageButton = new JButton(clearIcon);
		clearImageButton.setBorder(BorderFactory.createEmptyBorder());
		clearImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.files.clear();
				model.updateLayout();
			}
		});

		// create clear filter button
		clearFilterButton = new JButton("Clear");
		clearFilterButton.setForeground(Color.darkGray);
		clearFilterButton.setFont(new Font("SanSerif", Font.PLAIN, 18));
		clearFilterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JButton star: filter) {
					star.setIcon(emptyStarIcon);
				}
				model.filter(0);
			}
		});
		clearFilterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clearFilterButton.setForeground(Color.lightGray);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clearFilterButton.setForeground(Color.darkGray);
			}
		});
		clearFilterButton.setBorder(BorderFactory.createEmptyBorder());

		// add every component to the panel
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(gridButton);
		this.add(listButton);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		this.add(nameLabel);
		this.add(Box.createRigidArea(new Dimension(20, 0)));
		this.add(importButton);
		this.add(Box.createHorizontalGlue());
		this.add(clearImageButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(filterLabel);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		for (JButton star: filter) {
			this.add(star);
		}
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(clearFilterButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));

	}
}
