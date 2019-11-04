import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.swing.plaf.synth.SynthListUI;

class Cell {
	static final int size = 20;
	private Image snakeHead;
	private Image snakeBody;
	private Image leftDown;
	private Image rightDown;
	private Image leftUp;
	private Image rightUp;
	private Image snakeTail;
	private Image fruit;
	private Image border;
	private Image tile;


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
		this.border = new Image("file:src/assets/border.png", size, size, true, true);
		this.tile = new Image("file:src/assets/tile.png", size, size, true, true);
		this.snakeHead = new Image("file:src/assets/snakehead.png", size, size, true, true);
	}

	void paint(GraphicsContext gc, boolean snake, boolean fruit) {
		if (snake) {
			gc.setFill(Color.ROYALBLUE);
			gc.fillRect(xPoint, yPoint, size, size);
		} else if (fruit) {
			gc.setFill(Color.ORANGE);
			gc.fillRect(xPoint, yPoint, size, size);
		} else if (isBorder) {
			gc.setFill(Color.GREEN);
			gc.drawImage(border, xPoint, yPoint, size, size);
		} else {
			gc.setFill(Color.LIGHTGREEN);
			gc.drawImage(tile, xPoint, yPoint, size, size);
		}
	}

	void paintHead(GraphicsContext gc, Directions direction) {
		switch (direction) {
			case Up:
				snakeHead.
		}
		gc.drawImage();
	}

	void paintTail(GraphicsContext gc) {

	}
}
