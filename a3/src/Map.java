import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;


class Map extends Canvas {
	private static final int cellSize = 20;

	private int level;

	private GraphicsContext gc;

	private ArrayList<Cell> cells;
	private Snake snake;
	private ArrayList<Cell> currentFruit;
	private ArrayList<Cell> level1fruit;
	private ArrayList<Cell> level2fruit;
	private ArrayList<Cell> level3fruit;

	Map(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
		level = 1;
		cells = new ArrayList<>();
		for (int i = 0; i < 38; i++) {
			for (int j = 0; j < 50; j++) {
				Cell newCell;
				if (i == 0 || i == 37 || j == 0 || j == 49) {
					newCell = new Cell(cellSize * j, cellSize * i, true);
				} else {
					newCell = new Cell(cellSize * j, cellSize * i);
				}
				cells.add(newCell);
			}
		}
		snake = new Snake();
		snake.setHead(cells.get(51));
		snake.setTail(cells.get(50));

		// setup fruit for all three levels
		setupLevel1Fruit();
		setupLevel2Fruit();
		setupLevel3Fruit();

		currentFruit = level1fruit;
		snake.setFruits(currentFruit);


	}

	void setGraphicsContext(GraphicsContext graphicsContext) {
		gc = graphicsContext;
	}

	void paint() {
		for (Cell cell : cells) {
			cell.paint(gc, false);
		}
		for (Cell fruit : currentFruit) {
			fruit.paint(gc, true);
		}
		snake.paint(gc);
	}


	int updateAndRepaintSnake(Directions direction) {
		int result = snake.updateSnake(direction);
		if (result == Snake.complete) {
			snake.paint(gc);
		}
		return result;
	}

	void updateLevel(int level) {
		if (level == 1) {
			this.level = level;
			setupLevel1Fruit();
			currentFruit = level1fruit;
			snake.setFruits(currentFruit);
			paint();
		} else if (level == 2) {
			this.level = level;
			setupLevel2Fruit();
			currentFruit = level2fruit;
			snake.setFruits(currentFruit);
			paint();
		} else if (level == 3) {
			this.level = level;
			setupLevel3Fruit();
			currentFruit = level3fruit;
			snake.setFruits(currentFruit);
			paint();
		} else {
			System.err.println("Error: No such level " + level);
		}

	}

	int getLevel() {
		return level;
	}

	boolean getDidEat() {
		return snake.getDidEat();
	}

	private void setupLevel1Fruit() {
		level1fruit = new ArrayList<>();
		level1fruit.add(new Cell(100, 100));
		level1fruit.add(new Cell(140, 500));
		level1fruit.add(new Cell(500, 240));
		level1fruit.add(new Cell(840, 700));
		level1fruit.add(new Cell(400, 600));
	}

	private void setupLevel2Fruit() {
		level2fruit = new ArrayList<>();
		level2fruit.add(new Cell(120, 180));
		level2fruit.add(new Cell(180, 300));
		level2fruit.add(new Cell(300, 640));
		level2fruit.add(new Cell(640, 200));
		level2fruit.add(new Cell(800, 400));
		level2fruit.add(new Cell(40, 80));
		level2fruit.add(new Cell(120, 700));
		level2fruit.add(new Cell(380, 340));
		level2fruit.add(new Cell(600, 100));
		level2fruit.add(new Cell(200, 500));
	}

	private void setupLevel3Fruit() {
		level3fruit = new ArrayList<>();
		level3fruit.add(new Cell(300, 100));
		level3fruit.add(new Cell(240, 200));
		level3fruit.add(new Cell(600, 240));
		level3fruit.add(new Cell(240, 700));
		level3fruit.add(new Cell(700, 680));
		level3fruit.add(new Cell(200, 100));
		level3fruit.add(new Cell(440, 500));
		level3fruit.add(new Cell(200, 240));
		level3fruit.add(new Cell(640, 480));
		level3fruit.add(new Cell(800, 200));
		level3fruit.add(new Cell(800, 700));
		level3fruit.add(new Cell(640, 200));
		level3fruit.add(new Cell(800, 540));
		level3fruit.add(new Cell(440, 80));
		level3fruit.add(new Cell(200, 500));
	}

	void reset() {
		level = 1;
		cells = new ArrayList<>();
		for (int i = 0; i < 38; i++) {
			for (int j = 0; j < 50; j++) {
				Cell newCell;
				if (i == 0 || i == 37 || j == 0 || j == 49) {
					newCell = new Cell(cellSize * j, cellSize * i, true);
				} else {
					newCell = new Cell(cellSize * j, cellSize * i);
				}
				cells.add(newCell);
			}
		}
		snake = new Snake();
		snake.setHead(cells.get(51));
		snake.setTail(cells.get(50));

		// setup fruit for all three levels
		setupLevel1Fruit();
		setupLevel2Fruit();
		setupLevel3Fruit();

		currentFruit = level1fruit;
		snake.setFruits(currentFruit);
		paint();
	}
}
