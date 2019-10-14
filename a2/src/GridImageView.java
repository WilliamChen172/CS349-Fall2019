import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GridImageView extends JPanel {
	private ImageIcon originalImage;
	private ImageIcon thumbnailImage;
	private String imageName;
	private String creationDate;
	private JButton thumbnail;
	private JLabel name;
	private JLabel date;
	private BasicFileAttributes attributes;

	public GridImageView(File image) {
		// set layout for the whole panel
		this.setLayout(null);
		this.setPreferredSize(new Dimension(300, 400));
		this.setMaximumSize(new Dimension(300, 400));
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		if (image.exists()) {
			processFile(image);
		} else {
			System.out.println("Error: file doesn't exist");
		}

		thumbnail = new JButton(thumbnailImage);
		thumbnail.setPreferredSize(new Dimension(thumbnailImage.getIconWidth()-5, thumbnailImage.getIconHeight()-5));
		thumbnail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("IMAGE TAPPED");
			}
		});


		name = new JLabel(imageName);
		date = new JLabel(creationDate);

		this.add(thumbnail, BorderLayout.NORTH);
		this.add(name, BorderLayout.CENTER);
		this.add(date, BorderLayout.SOUTH);
		System.out.println("Image Name: " + imageName + " Created on: " + creationDate);
		System.out.println(image.getPath());
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
			System.out.println(inputImage.getHeight() + " " + inputImage.getWidth());
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
