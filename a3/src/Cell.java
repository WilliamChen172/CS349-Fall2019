import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Cell {
	static final int size = 20;
	private boolean isBorder;

	int xPoint;
	int yPoint;


	Cell(int xValue, int yValue) {
		this(xValue, yValue, false);
	}

	Cell(int xValue, int yValue, boolean border) {
		xPoint = xValue;
		yPoint = yValue;
		isBorder = border;
	}

	void paint(GraphicsContext gc, boolean snake, boolean fruit) {
		if (snake) {
			gc.setFill(Color.BLUE);
		} else if (fruit) {
			gc.setFill(Color.YELLOW);
		} else if (isBorder) {
			gc.setFill(Color.GREEN);
		} else {
			gc.setFill(Color.LIGHTGREEN);
		}
		gc.fillRect(xPoint, yPoint, size, size);
	}
}
