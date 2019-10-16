import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

	private String imageName;
	private String creationDate;

	private JPanel background;
	private JButton thumbnail;
	private ImageIcon thumbnailImage;

	private JLabel preview;
	private ImageIcon previewImage;

	private JLabel name;
	private JLabel date;


	private ArrayList<JButton> ratings;
	private JButton clearBtn;

	private BasicFileAttributes attributes;

	private ImageIcon emptyStar = new ImageIcon("./src/UI/star_empty.png");
	private ImageIcon fullStar = new ImageIcon("./src/UI/star_full.png");
	private ImageIcon emptyStarSmall = new ImageIcon("./src/UI/star_empty_small.png");
	private ImageIcon fullStarSmall = new ImageIcon("./src/UI/star_full_small.png");

	public int rating;
	private boolean isGrid;

	public ImageView(File image, Model model, boolean isGridView, int rateNum) {
		// set layout for the whole panel
		this.model = model;

		this.setLayout(null);
		if (isGridView) {
			this.setPreferredSize(new Dimension(302, 402));
			this.setMaximumSize(new Dimension(302, 402));
		} else {
			this.setPreferredSize(new Dimension(1002, 202));
			this.setMaximumSize(new Dimension(1002, 202));
		}
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBackground(Color.white);

		this.rating = rateNum;
		this.isGrid = isGridView;

		if (image.exists()) {
			processFile(image, isGridView);
			processPreview(image);
		} else {
			System.out.println("Error: file doesn't exist");
		}

		// Setup Thumbnail
		int xThumbnail;
		int yThumbnail;
		thumbnail = new JButton(thumbnailImage);
		thumbnail.setPreferredSize(new Dimension(thumbnailImage.getIconWidth(), thumbnailImage.getIconHeight()));
		if (thumbnailImage.getIconHeight() > thumbnailImage.getIconWidth()) {
			xThumbnail = (thumbnailImage.getIconHeight() - thumbnailImage.getIconWidth())/2 + 1;
			yThumbnail = 1;
		} else {
			xThumbnail = 1;
			yThumbnail = (thumbnailImage.getIconWidth() - thumbnailImage.getIconHeight())/2 + 1;
		}
		this.add(thumbnail);
		thumbnail.setBounds(xThumbnail,yThumbnail,thumbnailImage.getIconWidth(), thumbnailImage.getIconHeight());

		thumbnail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog modal = new JDialog(model.mainFrame, imageName, true);
				JPanel content = new JPanel();

				content.setMaximumSize(new Dimension(500, 600));
				content.setLayout(null);
				content.setBorder(BorderFactory.createEmptyBorder());
				content.setBackground(Color.white);

				// Setup Preview
				int xPreview;
				int yPreview;
				preview = new JLabel(previewImage);
				preview.setPreferredSize(new Dimension(previewImage.getIconWidth(), previewImage.getIconHeight()));
				if (previewImage.getIconHeight() > previewImage.getIconWidth()) {
					xPreview = (previewImage.getIconHeight() - previewImage.getIconWidth())/2;
					yPreview = 0;
				} else {
					xPreview = 0;
					yPreview = (previewImage.getIconWidth() - previewImage.getIconHeight())/2;
				}
				content.add(preview);
				preview.setBounds(xPreview,yPreview,previewImage.getIconWidth(), previewImage.getIconHeight());

				// Setup Divider
				JPanel divider = new JPanel();
				content.add(divider);
				divider.setBounds(0, 500, 500, 1);
				divider.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));

				// Setup Ratings
				for (int i = 0; i < 5; i++) {
					content.add(ratings.get(i));
					ratings.get(i).setBounds(50*i + 135, 525, 50, 50);
				}

				// Setup Clear Button
				content.add(clearBtn);
				clearBtn.setFont(new Font("SanSerif", Font.PLAIN, 18));
				clearBtn.setBounds(425, 525, 60, 30);

				// Setup Frame
				modal.setContentPane(content);
				modal.setMaximumSize(new Dimension(500, 600));
				modal.setPreferredSize(new Dimension(500, 600));
				modal.setMinimumSize(new Dimension(500, 600));
				modal.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				modal.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						if (isGridView) {
							for (int j = 0; j < 5; j++) {
								ImageView.this.add(ratings.get(j));
								ratings.get(j).setBounds(j*40+50, 335, 50, 50);
							}
							ImageView.this.add(clearBtn);
							clearBtn.setBounds(125, 380, 60, 15);
							clearBtn.setFont(new Font("SanSerif", Font.PLAIN, 15));
						} else {
							for (int j = 0; j < 5; j++) {
								ImageView.this.add(ratings.get(j));
								ratings.get(j).setBounds(j*50+750, 85, 50, 50);
							}
							ImageView.this.add(clearBtn);
							clearBtn.setBounds(845, 125, 60, 30);
							clearBtn.setFont(new Font("SanSerif", Font.PLAIN, 15));
						}

						updateRating(rating);
					}
				});
				modal.pack();
				modal.setLocationRelativeTo(model.mainFrame);
				modal.setVisible(true);
				modal.setResizable(false);
			}
		});

		// Setup Background Filler
		background = new JPanel();
		this.add(background);
		background.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 235), 1));
		//background.setBorder(BorderFactory.createEmptyBorder());
		background.setBackground(Color.white);

		// Setup Name Label
		name = new JLabel(imageName, SwingConstants.CENTER);
		name.setFont(new Font("SanSerif", Font.PLAIN, 18));
		this.add(name);

		// Setup Date Label
		date = new JLabel(creationDate, SwingConstants.CENTER);
		date.setFont(new Font("SanSerif", Font.PLAIN, 15));
		date.setForeground(Color.gray);
		this.add(date);

		// Setup Rating Labels
		ratings = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			JButton star = new JButton();
			if (i < rating) {
				if (isGrid) {
					star.setIcon(fullStarSmall);
				} else {
					star.setIcon(fullStar);
				}
			} else {
				if (isGrid) {
					star.setIcon(emptyStarSmall);
				} else {
					star.setIcon(emptyStar);
				}
			}
			star.setBorder(BorderFactory.createEmptyBorder());
			ratings.add(star);
			this.add(star);
			star.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						rating = ratings.indexOf(star) + 1;
						model.updateRating(image, rating);
						updateRating(rating);
				}
			});
		}

		clearBtn = new JButton("Clear");
		clearBtn.setForeground(Color.lightGray);
		clearBtn.setFont(new Font("SanSerif", Font.PLAIN, 12));
		this.add(clearBtn);
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JButton star: ratings) {
					if (isGridView) {
						star.setIcon(emptyStarSmall);
					} else {
						star.setIcon(emptyStar);
					}
				}
				rating = 0;
				model.updateRating(image, rating);
			}
		});
		clearBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clearBtn.setForeground(Color.white);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clearBtn.setForeground(Color.lightGray);
			}
		});
		clearBtn.setBorder(BorderFactory.createEmptyBorder());

		if (isGridView) {
			background.setBounds(0,0,302,302);
			name.setBounds(1,302,302,25);
			date.setBounds(1,322,302,25);
			for (int j = 0; j < 5; j++) {
				ratings.get(j).setBounds(j*40+50, 335, 50, 50);
			}
			clearBtn.setBounds(125, 380, 60, 15);
		} else {
			background.setBounds(0,0,202,202);
			name.setBounds(252,72,502,25);
			name.setHorizontalAlignment(SwingConstants.LEFT);
			name.setFont(new Font("SanSerif", Font.PLAIN, 21));
			date.setBounds(252,102,202,25);
			date.setFont(new Font("SanSerif", Font.PLAIN, 15));
			date.setHorizontalAlignment(SwingConstants.LEFT);
			for (int j = 0; j < 5; j++) {
				ratings.get(j).setBounds(j*50+750, 85, 50, 50);
			}
			clearBtn.setBounds(845, 125, 60, 30);
			clearBtn.setFont(new Font("SanSerif", Font.PLAIN, 15));
		}
	}

	private void updateRating(int rating) {
		for (int j = 0; j < rating; j++) {
			if (isGrid) {
				ratings.get(j).setIcon(fullStarSmall);
			} else {
				ratings.get(j).setIcon(fullStar);
			}
		}
		for (int k = rating; k < ratings.size(); k++) {
			if (isGrid) {
				ratings.get(k).setIcon(emptyStarSmall);
			} else {
				ratings.get(k).setIcon(emptyStar);
			}
		}
	}

	private void processPreview(File image) {
		try {
			attributes = Files.readAttributes(image.toPath(), BasicFileAttributes.class);
		} catch (IOException exception) {
			System.out.println("Exception handled when trying to get file " + "attributes: " + exception.getMessage());
			System.exit(0);
		}
		originalImage = new ImageIcon(image.getPath());
		try {
			int scaledWidth;
			int scaledHeight;

			BufferedImage inputImage = ImageIO.read(image);
			// gets the width/height ratio of the image and calculate scaled sizes
			if (inputImage.getHeight() > inputImage.getWidth()) {
				scaledHeight = 500;
				scaledWidth = scaledHeight * inputImage.getWidth() / inputImage.getHeight();
			} else if (inputImage.getWidth() > inputImage.getHeight()) {
				scaledWidth = 500;
				scaledHeight = scaledWidth * inputImage.getHeight() / inputImage.getWidth();
			} else {
				scaledHeight = 500;
				scaledWidth = 500;
			}
			// creates output image
			BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());
			// scales the input image to the output image
			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
			g2d.dispose();
			previewImage = new ImageIcon(outputImage);
		} catch (IOException exception) {
			System.out.println("Exception handled when trying to read file " + imageName + exception.getMessage());
		}
	}

	private void processFile(File image, boolean isGridView) {
		try {
			attributes = Files.readAttributes(image.toPath(), BasicFileAttributes.class);
		} catch (IOException exception) {
			System.out.println("Exception handled when trying to get file " + "attributes: " + exception.getMessage());
			System.exit(0);
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
			if (isGridView) {
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
			} else {
				// gets the width/height ratio of the image and calculate scaled sizes
				if (inputImage.getHeight() > inputImage.getWidth()) {
					scaledHeight = 200;
					scaledWidth = scaledHeight * inputImage.getWidth() / inputImage.getHeight();
				} else if (inputImage.getWidth() > inputImage.getHeight()) {
					scaledWidth = 200;
					scaledHeight = scaledWidth * inputImage.getHeight() / inputImage.getWidth();
				} else {
					scaledHeight = 200;
					scaledWidth = 200;
				}
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
