import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

class Display extends AnchorPane {
	private Label clock;
	private Label level;
	private Label score;

	Display() {
		clock = new Label("30");
		clock.setFont(new Font(50));
		AnchorPane.setTopAnchor(clock, 50.0);
		AnchorPane.setLeftAnchor(clock, 110.0);
		level = new Label("Level 1");
		level.setFont(new Font(30));
		AnchorPane.setLeftAnchor(level, 95.0);
		AnchorPane.setTopAnchor(level, 150.0);
		score = new Label(" Score: 0");
		score.setFont(new Font(30));
		AnchorPane.setLeftAnchor(score, 50.0);
		AnchorPane.setTopAnchor(score, 250.0);
		this.getChildren().addAll(clock, level, score);
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
