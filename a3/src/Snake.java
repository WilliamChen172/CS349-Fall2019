import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

enum Directions {
	Up,
	Down,
	Left,
	Right
}

class Snake {
	static final int error = 1;
	static final int complete = 0;

	private ArrayList<Cell> body;
	private Cell head;
	private Cell tail;
	private Cell oldTail;
	private Directions direction;
	private boolean didEat;

	private ArrayList<Cell> fruits;
	private ArrayList<Cell> overlapFruits;

	private int mapLength = 49;
	private int mapHeight = 37;

	Snake() {
		body = new ArrayList<>();
		direction = Directions.Right;
		overlapFruits = new ArrayList<>();
	}

	private Cell newHead(Directions direction) {
		Cell newHead;
		switch (direction) {
			case Up:
				newHead = new Cell(head.xPoint, head.yPoint - Cell.size);
				break;
			case Down:
				newHead = new Cell(head.xPoint, head.yPoint + Cell.size);
				break;
			case Left:
				newHead = new Cell(head.xPoint - Cell.size, head.yPoint);
				break;
			case Right:
				newHead = new Cell(head.xPoint + Cell.size, head.yPoint);
				break;
			default:
				System.err.println("ERROR. There is no direction " + direction);
				return new Cell(0, 0);
		}
		return newHead;
	}

	int updateSnake(Directions direction) {
		if (this.direction != direction) {
			this.direction = direction;
		}
		Cell newHead = newHead(direction);
		// Detect border collision
		if (newHead.xPoint == 0 || newHead.yPoint == 0 || newHead.xPoint == mapLength * Cell.size || newHead.yPoint == mapHeight * Cell.size) {
			return error;
		}

		// Detect self collision
		if (checkCellOverlap(newHead)) {
			return error;
		}
		// Detect fruit collision
		for (Cell fruit : fruits) {
			if (newHead.xPoint == fruit.xPoint && newHead.yPoint == fruit.yPoint) {
				didEat = true;
				grow(fruit, newHead);
				return complete;
			}
			if (checkCellOverlap(fruit)) {
				overlapFruits.add(fruit);
			}
		}

		if (overlapFruits.size() > 0) {
			didEat = true;
			grow(overlapFruits.remove(0), newHead);
			return complete;
		}

		if (body.isEmpty()) {
			oldTail = tail;
			tail = head;
		} else {
			oldTail = tail;
			tail = body.remove(body.size() - 1);
			body.add(0, head);
		}
		head = newHead;
		didEat = false;
		return complete;
	}


	void setHead(Cell headCell) {
		head = headCell;
	}

	void setTail(Cell tailCell) {
		tail = tailCell;
	}

	boolean getDidEat() {
		return didEat;
	}

	private void grow(Cell fruit, Cell newHead) {
		body.add(0, head);
		head = newHead;
		fruits.remove(fruit);
		while (fruits.size() < 15) {
			Cell newFruit = generateNewFruit();
			for (Cell oldfruit : fruits) {
				if (oldfruit.xPoint == newFruit.xPoint && oldfruit.yPoint == newFruit.yPoint) {
					continue;
				}
			}
			if (checkCellOverlap(newFruit)) {
				continue;
			}
			fruits.add(newFruit);
			break;
		}
	}

	void paint(GraphicsContext gc) {
		paintFruits(gc);
		gc.setFill(Color.YELLOW);
		head.paint(gc, true, false);
		tail.paint(gc, true, false);
		if (oldTail != null) {
			oldTail.paint(gc, false, false);
		}
		gc.setFill(Color.BLACK);
		for (Cell cell : body) {
			cell.paint(gc, true, false);
		}
	}

	private void paintFruits(GraphicsContext gc) {
		gc.setFill(Color.ORANGE);
		for (Cell fruit : fruits) {
			fruit.paint(gc, false, true);
		}
	}

	void setFruits(ArrayList<Cell> fruits) {
		this.fruits = fruits;
	}

	private Cell generateNewFruit() {
		Random rX = new Random();
		int newX = (rX.nextInt((48 - 1) + 1) + 1) * 20;
		Random rY = new Random();
		int newY = (rY.nextInt((36 - 1) + 1) + 1) * 20;
		return new Cell(newX, newY);
	}

	private boolean checkCellOverlap(Cell cell) {
		if (head.xPoint == cell.xPoint && head.yPoint == cell.yPoint) {
			return true;
		}
		if (tail.xPoint == cell.xPoint && tail.yPoint == cell.yPoint) {
			return true;
		}
		for (Cell body : body) {
			if (body.xPoint == cell.xPoint && body.yPoint == cell.yPoint) {
				return true;
			}
		}
		return false;
	}
}
