// HelloMVC: a simple MVC example
// the model is just a counter
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

/**
 * Two views with integrated controllers.
 */

import javax.swing.*;
import java.awt.*;

public class Main {

	public static void main(String[] args){
		JFrame frame = new JFrame("Fotag");

		// create Model and initialize it
		Model model = new Model();

		// create a layout panel to hold the two views
		MainView mainView = new MainView(model);
		model.addMainView(mainView);
		frame.getContentPane().add(mainView);
		// create the window
		frame.setPreferredSize(new Dimension(1120,1500));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
