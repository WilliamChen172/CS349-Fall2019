import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.canvas.*;
import javafx.stage.Stage;


public class Main extends Application {
    private static final int width = 1280;
    private static final int height = 800;
    private static final String name = "William Chen";
    private static final String userid = "w279chen";
    private static final String description = "Use the arrow keys to move around and eat fruit to get bigger!";
    private static final String startInstruction = "Press Q to exit.\nPress any other key to continue";
    private static final String gameover = "Game Over";
    private static final String exitInstruction = "Press R to restart. Press any other key to exit";

    private GraphicsContext context;
    private Runner runner;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        mainStage.setTitle("Snake");
        mainStage.setResizable(false);
        mainStage.setOnCloseRequest(event -> System.exit(0));
        mainStage.setMaxHeight(height);
        mainStage.setMaxWidth(width);

        AnchorPane menuPane = new AnchorPane();
        Label name = new Label(Main.name);
        Label userid = new Label(Main.userid);
        Label description = new Label(Main.description);
        Label instruction = new Label(Main.startInstruction);

        AnchorPane gamePane = new AnchorPane();
        Display display = new Display();

        AnchorPane endPane = new AnchorPane();
        Label gameover = new Label(Main.gameover);
        Label score = new Label();
        Label exitInstr = new Label(Main.exitInstruction);

        Scene mainScene = new Scene(menuPane, width, height);

        Map map = new Map(1000, 800);
        map.setFocusTraversable(true);
        map.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (runner.isActive()) {
                        runner.setDirection(Directions.Up);
                    }
                    break;
                case DOWN:
                    if (runner.isActive()) {
                        runner.setDirection(Directions.Down);
                    }
                    break;
                case LEFT:
                    if (runner.isActive()) {
                        runner.setDirection(Directions.Left);
                    }
                    break;
                case RIGHT:
                    if (runner.isActive()) {
                        runner.setDirection(Directions.Right);
                    }
                    break;
                case P:
                    runner.togglePause();
                    break;
                case R:
                    runner.reset();
                    break;
                case DIGIT1:
                    if (runner.isActive() && map.getLevel() != 1) {
                        map.updateLevel(1);
                        display.updateLevel(1);
                        display.updateClock(30);
                        runner.resetClock();
                    }
                    break;
                case DIGIT2:
                    if (runner.isActive() && map.getLevel() != 2) {
                        map.updateLevel(2);
                        display.updateLevel(2);
                        display.updateClock(30);
                        runner.resetClock();
                    }
                    break;
                case DIGIT3:
                    if (runner.isActive() && map.getLevel() != 3) {
                        map.updateLevel(3);
                        display.updateLevel(3);
                        display.updateClock(-1);
                    }
                    break;
                case Q:
                    map.setFocusTraversable(false);
                    mainScene.setRoot(endPane);
                    if (runner != null) {
                        score.setText("Score: " + runner.getScore());
                    } else {
                        score.setText("Score: 0");
                    }
                    endPane.requestFocus();
                    break;
            }
        });
        context = map.getGraphicsContext2D();
        map.setGraphicsContext(context);
        map.paint();

        menuPane.getChildren().addAll(name, userid, description, instruction);
        menuPane.requestFocus();
        menuPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.Q) {
                mainScene.setRoot(endPane);
                if (runner != null) {
                    score.setText("Score: " + runner.getScore());
                } else {
                    score.setText("Score: 0");
                }
                endPane.requestFocus();
            } else {
                mainScene.setRoot(gamePane);
                map.reset();
                display.reset();
                map.requestFocus();
                runner = new Runner(map, display);
                Thread runner = new Thread(this.runner);
                runner.start();
                Thread listener = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            runner.join();
                        } catch (Exception ignored) {
                        } finally {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (!Main.this.runner.getReset()) {
                                        mainScene.setRoot(endPane);
                                        endPane.requestFocus();
                                        score.setText("Score: " + Main.this.runner.getScore());
                                    } else {
                                        mainScene.setRoot(menuPane);
                                        menuPane.requestFocus();
                                    }
                                }
                            });
                        }
                    }
                });
                listener.start();
            }
        });
        AnchorPane.setLeftAnchor(name, 400.0);
        AnchorPane.setTopAnchor(name, 300.0);
        AnchorPane.setLeftAnchor(userid, 400.0);
        AnchorPane.setTopAnchor(userid, 350.0);
        AnchorPane.setLeftAnchor(description, 400.0);
        AnchorPane.setTopAnchor(description, 400.0);
        AnchorPane.setLeftAnchor(instruction, 400.0);
        AnchorPane.setTopAnchor(instruction, 450.0);

        gamePane.getChildren().addAll(display, map);
        AnchorPane.setLeftAnchor(map, 280.0);
        AnchorPane.setTopAnchor(map, 0.0);
        AnchorPane.setRightAnchor(map, 0.0);
        AnchorPane.setTopAnchor(display, 0.0);

        endPane.getChildren().addAll(gameover, score, exitInstr);
        AnchorPane.setLeftAnchor(gameover, 400.0);
        AnchorPane.setTopAnchor(gameover, 300.0);
        AnchorPane.setLeftAnchor(score, 400.0);
        AnchorPane.setTopAnchor(score, 350.0);
        AnchorPane.setLeftAnchor(exitInstr, 400.0);
        AnchorPane.setTopAnchor(exitInstr, 400.0);
        endPane.requestFocus();
        endPane.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.R) {
                mainScene.setRoot(menuPane);
                menuPane.requestFocus();
                runner = null;
            } else if (code != KeyCode.UP && code != KeyCode.DOWN && code != KeyCode.LEFT && code != KeyCode.RIGHT) {
                System.exit(0);
            }
        });

        mainStage.setScene(mainScene);
        mainStage.show();
    }
}