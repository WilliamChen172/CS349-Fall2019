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
		head.paintHead(gc, direction);
		Cell last;
		if (body.size() > 0) {
			last = body.get(body.size() - 1);
		} else {
			last = head;
		}
			if (last.xPoint > tail.xPoint) {
				tail.paintTail(gc, Directions.Right);
			} else if (last.xPoint < tail.xPoint) {
				tail.paintTail(gc, Directions.Left);
			} else if (last.yPoint > tail.yPoint) {
				tail.paintTail(gc, Directions.Down);
			} else if (last.yPoint < tail.yPoint) {
				tail.paintTail(gc, Directions.Up);
			}
		if (oldTail != null) {
			oldTail.paint(gc, false);
		}
		for (Cell cell : body) {
			Cell prev;
			Cell next;
			if (body.size() == 1) {
				prev =  head;
				next = tail;
			} else if (body.indexOf(cell) == 0) {
				prev = head;
				next = body.get(body.indexOf(cell) + 1);
			} else if (body.indexOf(cell) == body.size() - 1) {
				prev = body.get(body.indexOf(cell) - 1);
				next = tail;
			} else {
				prev = body.get(body.indexOf(cell) - 1);
				next = body.get(body.indexOf(cell) + 1);
			}
			if ((prev.xPoint < next.xPoint && prev.yPoint < next.yPoint && cell.yPoint == prev.yPoint) ||
					(prev.xPoint > next.xPoint && prev.yPoint > next.yPoint && cell.yPoint == next.yPoint)) {
				cell.paintLeftDown(gc);
			} else if ((prev.xPoint < next.xPoint && prev.yPoint > next.yPoint && cell.yPoint == prev.yPoint) ||
					(prev.xPoint > next.xPoint && prev.yPoint < next.yPoint && cell.yPoint == next.yPoint)) {
				cell.paintLeftUp(gc);
			} else if ((prev.xPoint < next.xPoint && prev.yPoint > next.yPoint && cell.yPoint == next.yPoint) ||
					(prev.xPoint > next.xPoint && prev.yPoint < next.yPoint && cell.yPoint == prev.yPoint)) {
				cell.paintRightUp(gc);
			} else if ((prev.xPoint < next.xPoint && prev.yPoint < next.yPoint && cell.yPoint == next.yPoint) ||
					(prev.xPoint > next.xPoint && prev.yPoint > next.yPoint && cell.yPoint == prev.yPoint)) {
				cell.paintRightDown(gc);
			} else {
				cell.paintSnakeBody(gc);
			}
		}
	}

	private void paintFruits(GraphicsContext gc) {
		for (Cell fruit : fruits) {
			fruit.paint(gc, true);
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
