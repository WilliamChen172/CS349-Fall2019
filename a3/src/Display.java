import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

class Display extends VBox {
	private Label clock;
	private Label level;
	private Label score;

	Display() {
		clock = new Label("30");
		level = new Label("Level 1");
		score = new Label(" Score: 0");
		this.getChildren().addAll(clock, level, score);
		this.setSpacing(100);
		this.setPadding(new Insets(10));
	}

	void updateClock(int clock) {
		if (clock == -1) {
			this.clock.setText("");
		} else {
			this.clock.setText(String.valueOf(clock));
		}
	}

	void updateLevel(int level) {
		this.level.setText("Level " + level);
	}

	void updateScore(int score) {
		this.score.setText("Score: " + score);
	}

	void reset() {
		clock.setText("30");
		level.setText("Level 1");
		score.setText("Score: 0");
	}
}
