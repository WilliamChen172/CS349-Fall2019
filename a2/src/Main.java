// HelloMVC: a simple MVC example
// the model is just a counter
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

/**
 * Two views with integrated controllers.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main {

	public static void main(String[] args){
		JFrame frame = new JFrame("Fotag");

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
				if (model.isGridView) {
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
	}
}
