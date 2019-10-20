import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Fotag");
		File savedList = new File("./savedList");

		// create Model and initialize it
		Model model = new Model();
		model.addMainFrame(frame);

		// create a layout panel to hold the two views
		MainView mainView = new MainView(model);
		model.addMainView(mainView);
		frame.getContentPane().add(mainView);

		// create the window
		frame.setPreferredSize(new Dimension(1120,1500));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (model.isGridView()) {
					frame.setMinimumSize(new Dimension(420, 550));
				} else {
					frame.setMinimumSize(new Dimension(1120, 300));
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

		// read any saved information
		if (savedList.exists()) {
			try(BufferedReader reader = new BufferedReader(new FileReader(savedList))) {
				String line;
				int size = 0;
				File[] files;
				ArrayList<Integer> ratings;
				int filter = 0;
				if ((line = reader.readLine()) != null) {
					size = Integer.parseInt(line);
					files = new File[size];
					ratings = new ArrayList<>();
					for (int i = 0; i < size; i++) {
						if ((line = reader.readLine()) != null) {
							files[i] = new File(line);
						}
						if ((line = reader.readLine()) != null) {
							ratings.add(Integer.parseInt(line));
						}
					}
					if ((line = reader.readLine()) != null) {
						filter = Integer.parseInt(line);
					}
					model.ratings = ratings;
					model.setFilterRating(filter);
					model.addFile(files);
				}
			} catch (IOException e) {
				// TODO
				e.printStackTrace();
			}
		} else {
			savedList.createNewFile();
		}

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				FileWriter writer;
				try {
					writer = new FileWriter(savedList);
					writer.flush();
					writer.write(model.files.size() + "\n");
					for (int i = 0; i < model.files.size(); i++) {
						writer.write(model.files.get(i) + "\n");
						writer.write(model.ratings.get(i) + "\n");
					}
					writer.write(model.getFilterRating() + "\n");
					writer.close();
				} catch (IOException ex) {
					// TODO
					ex.printStackTrace();
				}
			}
		});
	}
}
