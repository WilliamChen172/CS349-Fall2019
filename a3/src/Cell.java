import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.swing.plaf.synth.SynthListUI;

class Cell {
	static final int size = 20;
	private Image snakeHeadUp;
	private Image snakeHeadDown;
	private Image snakeHeadLeft;
	private Image snakeHeadRight;

	private Image snakeBody;

	private Image leftDown;
	private Image rightDown;
	private Image leftUp;
	private Image rightUp;

	private Image snakeTailUp;
	private Image snakeTailDown;
	private Image snakeTailLeft;
	private Image snakeTailRight;

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
		this.fruit = new Image("file:src/assets/fruit.png", size, size, true, true);
		this.snakeBody = new Image("file:src/assets/body.png", size, size, true, true);
		this.snakeHeadUp = new Image("file:src/assets/snakeheadup.png", size, size, true, true);
		this.snakeHeadDown = new Image("file:src/assets/snakeheaddown.png", size, size, true, true);
		this.snakeHeadLeft = new Image("file:src/assets/snakeheadleft.png", size, size, true, true);
		this.snakeHeadRight = new Image("file:src/assets/snakeheadright.png", size, size, true, true);
		this.snakeTailUp = new Image("file:src/assets/snaketailup.png", size, size, true, true);
		this.snakeTailDown = new Image("file:src/assets/snaketaildown.png", size, size, true, true);
		this.snakeTailLeft = new Image("file:src/assets/snaketailleft.png", size, size, true, true);
		this.snakeTailRight = new Image("file:src/assets/snaketailright.png", size, size, true, true);
		this.leftDown = new Image("file:src/assets/leftdown.png", size, size, true, true);
		this.leftUp = new Image("file:src/assets/leftup.png", size, size, true, true);
		this.rightDown = new Image("file:src/assets/rightdown.png", size, size, true, true);
		this.rightUp = new Image("file:src/assets/rightup.png", size, size, true, true);
	}

	void paint(GraphicsContext gc, boolean isFruit) {
		if (isFruit) {
			gc.drawImage(fruit, xPoint, yPoint, size, size);
		} else if (isBorder) {
			gc.drawImage(border, xPoint, yPoint, size, size);
		} else {
			gc.drawImage(tile, xPoint, yPoint, size, size);
		}
	}

	void paintHead(GraphicsContext gc, Directions direction) {
		switch (direction) {
			case Up:
				gc.drawImage(snakeHeadUp, xPoint, yPoint, size, size);
				break;
			case Down:
				gc.drawImage(snakeHeadDown, xPoint, yPoint, size, size);
				break;
			case Left:
				gc.drawImage(snakeHeadLeft, xPoint, yPoint, size, size);
				break;
			case Right:
				gc.drawImage(snakeHeadRight, xPoint, yPoint, size, size);
				break;
		}
	}

	void paintTail(GraphicsContext gc, Directions direction) {
		gc.drawImage(tile, xPoint, yPoint, size, size);
		switch (direction) {
			case Up:
				gc.drawImage(snakeTailUp, xPoint, yPoint, size, size);
				break;
			case Down:
				gc.drawImage(snakeTailDown, xPoint, yPoint, size, size);
				break;
			case Left:
				gc.drawImage(snakeTailLeft, xPoint, yPoint, size, size);
				break;
			case Right:
				gc.drawImage(snakeTailRight, xPoint, yPoint, size, size);
				break;
		}
	}

	void paintSnakeBody(GraphicsContext gc) {
		gc.drawImage(snakeBody, xPoint, yPoint, size, size);
	}
}
