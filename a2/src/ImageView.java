import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImageView extends JPanel {
	private Model model;

	private ImageIcon originalImage;
	private ImageIcon thumbnailImage;
	private String imageName;
	private String creationDate;
	private JButton thumbnail;
	private JLabel name;
	private JLabel date;


	private JPanel background;
	private ArrayList<JButton> ratings;
	private JButton clearRating;
	private BasicFileAttributes attributes;

	private ImageIcon emptyStar = new ImageIcon("./src/UI/star_empty.png");
	private ImageIcon fullStar = new ImageIcon("./src/UI/star_full.png");

	public int rating;

	public ImageView(File image, Model model, boolean isGridView) {
		// set layout for the whole panel
		this.model = model;

		this.setLayout(null);
		if (isGridView) {
			this.setPreferredSize(new Dimension(302, 402));
			this.setMaximumSize(new Dimension(302, 402));
		} else {
			this.setPreferredSize(new Dimension(602, 302));
			this.setMaximumSize(new Dimension(602, 302));
		}
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(Color.white);

		if (image.exists()) {
			processFile(image);
		} else {
			System.out.println("Error: file doesn't exist");
		}

		// Setup Thumbnail
		int xInset;
		int yInset;
		thumbnail = new JButton(thumbnailImage);
		thumbnail.setPreferredSize(new Dimension(thumbnailImage.getIconWidth(), thumbnailImage.getIconHeight()));
		if (thumbnailImage.getIconHeight() > thumbnailImage.getIconWidth()) {
			xInset = (thumbnailImage.getIconHeight() - thumbnailImage.getIconWidth())/2 + 1;
			yInset = 1;
		} else {
			xInset = 1;
			yInset = (thumbnailImage.getIconWidth() - thumbnailImage.getIconHeight())/2 + 1;
		}
		this.add(thumbnail);
		thumbnail.setBounds(xInset,yInset,thumbnailImage.getIconWidth(), thumbnailImage.getIconHeight());
		thumbnail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("IMAGE TAPPED");
			}
		});

		background = new JPanel();
		this.add(background);
		background.setBounds(0,0,302,302);
		background.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		background.setBackground(Color.white);

		// Setup Name Label
		name = new JLabel(imageName, SwingConstants.CENTER);
		name.setFont(new Font("SanSerif", Font.BOLD, 15));
		this.add(name);

		// Setup Date Label
		date = new JLabel(creationDate, SwingConstants.CENTER);
		date.setFont(new Font("SanSerif", Font.PLAIN, 12));
		this.add(date);

		// Setup Rating Labels
		ratings = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			JButton star = new JButton(emptyStar);
			star.setBorder(BorderFactory.createEmptyBorder());
			ratings.add(star);
			this.add(star);
			star.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (star.getIcon().equals(emptyStar)) {
						for (int j = 0; j <= ratings.indexOf(star); j++) {
							ratings.get(j).setIcon(fullStar);
						}
						rating = ratings.indexOf(star) + 1;
						model.updateRating(image, rating);
					} else {
						for (int k = ratings.indexOf(star) + 1; k < ratings.size(); k++) {
							ratings.get(k).setIcon(emptyStar);
						}
						rating = ratings.indexOf(star) + 1;
						model.updateRating(image, rating);
					}
				}
			});
		}

		clearRating = new JButton("Clear");
		clearRating.setForeground(Color.lightGray);
		clearRating.setFont(new Font("SanSerif", Font.PLAIN, 12));
		this.add(clearRating);
		clearRating.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JButton star: ratings) {
					star.setIcon(emptyStar);
				}
				rating = 0;
				model.updateRating(image, rating);
			}
		});
		clearRating.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clearRating.setForeground(Color.white);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clearRating.setForeground(Color.lightGray);
			}
		});
		clearRating.setBorder(BorderFactory.createEmptyBorder());

		if (isGridView) {
			name.setBounds(1,302,302,25);
			date.setBounds(1,322,302,25);
			for (int j = 0; j < 5; j++) {
				ratings.get(j).setBounds(j*30+75, 345, 30, 30);
			}
			clearRating.setBounds(120, 380, 60, 15);
		} else {
			name.setBounds(302,2,302,25);
			date.setBounds(302,22,302,25);
			for (int j = 0; j < 5; j++) {
				ratings.get(j).setBounds(j*30+375, 45, 30, 30);
			}
			clearRating.setBounds(420, 90, 60, 15);
		}
	}

	private void processFile(File image) {
		try {
			attributes = Files.readAttributes(image.toPath(), BasicFileAttributes.class);
		} catch (IOException exception) {
			System.out.println("Exception handled when trying to get file " + "attributes: " + exception.getMessage());
		}
		imageName = image.getName();
		FileTime time = attributes.creationTime();
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		creationDate = simpleDateFormat.format(new Date(time.toMillis()));
		originalImage = new ImageIcon(image.getPath());
		try {
			int scaledWidth;
			int scaledHeight;

			BufferedImage inputImage = ImageIO.read(image);

			// gets the width/height ratio of the image and calculate scaled sizes
			if (inputImage.getHeight() > inputImage.getWidth()) {
				scaledHeight = 300;
				scaledWidth = scaledHeight * inputImage.getWidth() / inputImage.getHeight();
			} else if (inputImage.getWidth() > inputImage.getHeight()) {
				scaledWidth = 300;
				scaledHeight = scaledWidth * inputImage.getHeight() / inputImage.getWidth();
			} else {
				scaledHeight = 300;
				scaledWidth = 300;
			}

			// creates output image
			BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
			// scales the input image to the output image
			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
			g2d.dispose();
			thumbnailImage = new ImageIcon(outputImage);
		} catch (IOException exception) {
			System.out.println("Exception handled when trying to read file " + imageName + exception.getMessage());
		}
	}
}
