package controller;

import games.Pair;
import games.Point;
import games.RunCrosswordGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CrosswordGameController extends Controller implements Initializable {
    public FlowPane getMatrixFlowPane() {
        return matrixFlowPane;
    }

    @FXML
    private AnchorPane rootAnchorPane;

    private ProgressIndicator loadingProgressIndicator;

    @FXML
    private FlowPane matrixFlowPane;

    @FXML
    private ListView<String> wordListView;

    @FXML
    private Button replayButton;

    @FXML
    private Button hintButton;

    @FXML
    private Button nextLevelButton;

    @FXML
    private Line crossLine;
    private int startX, startY;
    private int endX, endY;

    private Set<Pair> pairSet;

    private LocalTime time;
    @FXML
    private Label timerLabel;
    private DateTimeFormatter formatter;
    private boolean isTimeUp;

    private Timeline timeline;

    public void loadMatrix() {
        replayButton.setVisible(false);
        nextLevelButton.setVisible(false);
        updateWordListView();
        wordListView.setPrefHeight(wordListView.getItems().size() * 25);
        wordListView.setMaxWidth(200);
        rootAnchorPane.getChildren().clear();
        rootAnchorPane.getChildren().addAll(matrixFlowPane, hintButton, nextLevelButton, wordListView, crossLine, timerLabel, replayButton, loadingProgressIndicator);
        matrixFlowPane.getChildren().clear();
        matrixFlowPane.setPrefWidth(RunCrosswordGame.getRunCrosswordGame().getMatrix().size() * (35.2 + matrixFlowPane.getVgap()) + 25);
        matrixFlowPane.setLayoutX((1402 - matrixFlowPane.getPrefWrapLength()) / 2);
        matrixFlowPane.setLayoutY(100);
        matrixFlowPane.setDisable(false);
        crossLine.setLayoutX(matrixFlowPane.getLayoutX());
        crossLine.setLayoutY(matrixFlowPane.getLayoutY());
        for (ArrayList<Character> arr : RunCrosswordGame.getRunCrosswordGame().getMatrix()) {
            for (Character c : arr) {
                Button button = new Button();
                button.setMinWidth(35.2);
                button.setMinHeight(35.2);
                button.setText(String.valueOf(c).toUpperCase());
                button.setStyle("-fx-background-color: transparent; -fx-font-size: 15px;");
                button.setOnMousePressed(event -> {
                    crossLine.setVisible(true);
                    startX = (int) ((int) (event.getSceneX() - matrixFlowPane.getLayoutX() - rootAnchorPane.getLayoutX()) / 35.2);
                    startY = (int) ((int) (event.getSceneY() - matrixFlowPane.getLayoutY() - rootAnchorPane.getLayoutY()) / 35.2);
                    System.out.println(startX + " " + startY);
                    crossLine.setStartX(startX * 35.2 + 17.6);
                    crossLine.setStartY(startY * 35.2 + 17.6);
                    crossLine.setEndX(crossLine.getStartX());
                    crossLine.setEndY(crossLine.getStartY());
                });

                button.setOnMouseDragged(event -> {
                    int x = (int) ((int) (event.getSceneX() - matrixFlowPane.getLayoutX() - rootAnchorPane.getLayoutX()) / 35.2);
                    int y = (int) ((int) (event.getSceneY() - matrixFlowPane.getLayoutY() - rootAnchorPane.getLayoutY()) / 35.2);

                    int diffX = x - startX;
                    int diffY = y - startY;

                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        crossLine.setEndX(crossLine.getStartX() + diffX * 35.2);
                        crossLine.setEndY(crossLine.getStartY());
                    } else if (Math.abs(diffY) > Math.abs(diffX)) {
                        crossLine.setEndX(crossLine.getStartX());
                        crossLine.setEndY(crossLine.getStartY() + diffY * 35.2);
                    } else {
                        crossLine.setEndX(crossLine.getStartX() + diffX * 35.2);
                        crossLine.setEndY(crossLine.getStartY() + diffY * 35.2);
                    }

                });

                button.setOnMouseReleased(event -> {
                    double posEndX = Math.round((crossLine.getEndX()) * 10.0) / 10.0;
                    double posEndY = Math.round((crossLine.getEndY()) * 10.0) / 10.0;
                    endX = (int) ((Math.round((posEndX) * 10.0) / 10.0) / 35.2);
                    endY = (int) ((Math.round((posEndY) * 10.0) / 10.0) / 35.2);
                    Pair pair = new Pair(new Point(startY, startX), new Point(endY, endX));
                    System.out.println(pair);
                    if (RunCrosswordGame.getRunCrosswordGame().getWordList().containsKey(pair)) {
                        createLine(startX, startY, endX, endY);
                        RunCrosswordGame.getRunCrosswordGame().getWordList().remove(pair);
                        updateWordListView();
                    }
                    crossLine.setVisible(false);
                    if (wordListView.getItems().isEmpty()) {
                        if (RunCrosswordGame.getRunCrosswordGame().getSIZE() < 19) {
                            nextLevelButton.setVisible(true);
                        } else {
                            replayButton.setVisible(true);
                        }
                    }
                });
                matrixFlowPane.getChildren().add(button);
            }
        }
        wordListView.setVisible(true);
        hintButton.setVisible(true);
        timerLabel.setVisible(true);
//        buffBan();
    }

    public void countTime() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!isTimeUp) {
                time = time.minusSeconds(1);
                if (time.isBefore(LocalTime.of(0, 0, 1))) {
                    isTimeUp = true;
                    timerLabel.setText("Time's up!");
                    replayButton.setVisible(true);
                    matrixFlowPane.setDisable(true);
                } else {
                    timerLabel.setText(time.format(formatter));
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void buffBan() {
        for (Map.Entry<Pair, String> entry : RunCrosswordGame.getRunCrosswordGame().getWordList().entrySet()) {
            int startX = entry.getKey().getStart().getY();
            int startY = entry.getKey().getStart().getX();
            int endX = entry.getKey().getTarget().getY();
            int endY = entry.getKey().getTarget().getX();
            createLine(startX, startY, endX, endY);
        }
    }

    public void createLine(int startX, int startY, int endX, int endY) {
        Line line = new Line();
        line.setStartX(startX * 35.2 + 17.6);
        line.setStartY(startY * 35.2 + 17.6);
        line.setEndX(endX * 35.2 + 17.6);
        line.setEndY(endY * 35.2 + 17.6);
        line.setLayoutX(matrixFlowPane.getLayoutX());
        line.setLayoutY(matrixFlowPane.getLayoutY());
        line.setStrokeWidth(2);
        line.setStroke(Color.RED);
        line.setOpacity(0.5);
        rootAnchorPane.getChildren().add(line);
    }

    @FXML
    void getHint(MouseEvent event) {
        if (!RunCrosswordGame.getRunCrosswordGame().getWordList().isEmpty() && pairSet.size() != 12) {
            Object[] keys = RunCrosswordGame.getRunCrosswordGame().getWordList().keySet().toArray();
            Pair randomKey = (Pair) keys[new Random().nextInt(keys.length)];
            int sizeBefore = pairSet.size();
            pairSet.add(randomKey);
            if (sizeBefore != pairSet.size()) {
                int X = randomKey.getStart().getX();
                int Y = randomKey.getStart().getY();
                matrixFlowPane.getChildren().get(X * RunCrosswordGame.getRunCrosswordGame().getSIZE() + Y).setStyle("-fx-background-color: transparent; -fx-font-size: 15px; -fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                getHint(event);
            }
        }

    }

    @FXML
    void nextLevel(MouseEvent event) {
        restart(RunCrosswordGame.getRunCrosswordGame().getSIZE() + 1);
    }

    @FXML
    void replay(MouseEvent event) {
        if (RunCrosswordGame.getRunCrosswordGame().getSIZE() == 19) {
            restart(12);
        } else {
            restart(RunCrosswordGame.getRunCrosswordGame().getSIZE());
        }
    }

    public void restart(int size) {
        replayButton.setVisible(false);
        hintButton.setDisable(true);
        nextLevelButton.setVisible(false);
        startLoading();
        pairSet = new HashSet<>();
        RunCrosswordGame.getRunCrosswordGame().setSIZE(size);
        Task<Void> task = RunCrosswordGame.getRunCrosswordGame().createTask();
        new Thread(task).start();
        task.setOnRunning(e -> {
        });
        task.setOnSucceeded(e -> {
            endLoading();
            loadMatrix();
//            replayButton.setVisible(true);
            hintButton.setDisable(false);
//            nextLevelButton.setVisible(true);
            isTimeUp = false;
            time = LocalTime.of(0, 5, 0);
        });
    }

    public void startLoading() {
        loadingProgressIndicator.setVisible(true);
    }

    public void endLoading() {
        loadingProgressIndicator.setVisible(false);
    }



    public void updateWordListView() {
        wordListView.getItems().clear();
        for (Map.Entry<Pair, String> entry : RunCrosswordGame.getRunCrosswordGame().getWordList().entrySet()) {
            String s = entry.getValue();
            wordListView.getItems().add(s);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadingProgressIndicator = new ProgressIndicator();
        rootAnchorPane.getChildren().add(loadingProgressIndicator);
        pairSet = new HashSet<>();
        wordListView = new ListView<>();
        formatter = DateTimeFormatter.ofPattern("mm:ss");
        isTimeUp = false;
        time = LocalTime.of(0, 5, 0);
        hintButton.setCursor(Cursor.HAND);
        nextLevelButton.setCursor(Cursor.HAND);
        replayButton.setCursor(Cursor.HAND);
        loadingProgressIndicator.setProgress(-1);
        hintButton.setLayoutX(20);
        hintButton.setLayoutY(20);
        nextLevelButton.setLayoutX(20);
        nextLevelButton.setLayoutY(60);
        wordListView.setLayoutX(20);
        wordListView.setLayoutY(100);
        timerLabel.setLayoutX((1402 - timerLabel.getWidth()) / 2);
        timerLabel.setLayoutY(60);
        replayButton.setLayoutX((1402 - replayButton.getWidth()) / 2);
        replayButton.setLayoutY(20);
        loadingProgressIndicator.setLayoutX((1402 - loadingProgressIndicator.getWidth()) / 2);
        loadingProgressIndicator.setLayoutY((500 - loadingProgressIndicator.getHeight()) / 2);
    }
}
