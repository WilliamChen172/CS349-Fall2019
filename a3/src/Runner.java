import javafx.application.Platform;

public class Runner implements Runnable {
	private static final int fruitScore = 100;
	private static final int levelScore = 500;
	private static final int clockCount = 30;
	private static final int disableClock = -1;
	private static final int level1 = 1;
	private static final int level2 = 2;
	private static final int level3 = 3;
	private static final int level1speed = 15;
	private static final int level2speed = 20;
	private static final int level3speed = 25;
	private static final float oneSecond = 1000.0f;

	private Map map;
	private Display display;
	private float frameRate;
	private float interval;
	private int frameTimer;
	private int clock;
	private int score;

	private boolean isRunning;
	private boolean isActive;
	private boolean isReset;

	private Directions curDirection;

	Runner(Map map, Display display) {
		this.map = map;
		this.display = display;
		frameRate = level1speed;
		interval = oneSecond / frameRate;
		isRunning = true;
		curDirection = Directions.Right;
		frameTimer = 0;
		clock = clockCount;
		score = 0;
		isActive = true;
		map.updateAndRepaintSnake(curDirection);
	}

	@Override
	public void run() {
		while (isActive) {
			float time = System.currentTimeMillis();

			time = System.currentTimeMillis() - time;
			if (isRunning) {
				int result = map.updateAndRepaintSnake(curDirection);
				if (result == Snake.error) {
					isReset = false;
					return;
				}
				if (map.getDidEat()) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							score += fruitScore;
							display.updateScore(score);
						}
					});
				}

				if (frameTimer == frameRate) {
					if (map.getLevel() != level3) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								display.updateClock(--clock);
							}
						});
					}
					if (clock == 0 && map.getLevel() == level1) {
						map.updateLevel(level2);
						clock = clockCount;
						score += levelScore;
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								display.updateClock(clock);
								display.updateLevel(level2);
								display.updateScore(score);
							}
						});
						updateInterval(level2);
					} else if (clock == 0 && map.getLevel() == level2) {
						map.updateLevel(level3);
						score += levelScore;
						clock = clockCount;
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								display.updateClock(disableClock);
								display.updateLevel(level3);
								display.updateScore(score);
							}
						});
						updateInterval(level3);
					}
					frameTimer = 0;
				}
			}
			// Adjust the timing correctly
			if (time < interval) {
				try {
					frameTimer++;
					Thread.sleep((long) (interval - time));
				} catch (InterruptedException ignore) {
				}
			}
		}
	}


	void togglePause() {
		isRunning = !isRunning;
	}

	void reset() {
		isReset = true;
		isActive = false;
	}

	boolean getReset() {
		return isReset;
	}
	boolean isActive() {
		return isActive;
	}

	void resetClock() {
		clock = clockCount;
	}

	void setDirection(Directions direction) {
		if (((curDirection == Directions.Up || curDirection == Directions.Down) &&
				(direction == Directions.Left || direction == Directions.Right))
		|| ((curDirection == Directions.Left || curDirection == Directions.Right) &&
				(direction == Directions.Up || direction == Directions.Down))) {
			curDirection = direction;
		}
	}

	int getScore() {
		return score;
	}

	void updateInterval(int level) {
		if (level == 1) {
			frameRate = level1speed;
		} else if (level == 2) {
			frameRate = level2speed;
		} else if (level == 3) {
			frameRate = level3speed;
		}
		interval = oneSecond/frameRate;
	}
}
