package controller;

import games.Pair;
import games.Point;
import games.RunCrosswordGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.*;

public class CrosswordGameController implements Initializable {
    public FlowPane getMatrixFlowPane() {
        return matrixFlowPane;
    }

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private FlowPane matrixFlowPane;

    @FXML
    private Button hintButton;

    @FXML
    private Line crossLine;
    private int startX, startY;
    private int endX, endY;

    private Set<Pair> pairSet;

    public void loadMatrix() {
        matrixFlowPane.setLayoutX(300);
        matrixFlowPane.setLayoutY(50);
        hintButton.setLayoutX(800);
        matrixFlowPane.getChildren().clear();
        matrixFlowPane.setPrefWidth(RunCrosswordGame.getRunCrosswordGame().getMatrix().size() * (35.2 + matrixFlowPane.getVgap()) + 25);
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
                    crossLine.setStartX(matrixFlowPane.getLayoutX() - 9 * 35 + rootAnchorPane.getLayoutX() + startX * 35.2 + 17.6);
                    crossLine.setStartY(matrixFlowPane.getLayoutY() - 5 * 35 + 17.6 + rootAnchorPane.getLayoutY() + startY * 35.2 + 17.6);
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
                    endX = (int) ((crossLine.getEndX() + 113) / 35.2);
                    endY = (int) ((crossLine.getEndY() - 0.6) / 35.2);
                    Pair pair = new Pair(new Point(startY, startX), new Point(endY, endX));
                    if (RunCrosswordGame.getRunCrosswordGame().getWordList().containsKey(pair)) {
                        createLine(startX, startY, endX, endY);
                    }
                    crossLine.setVisible(false);
                });
                matrixFlowPane.getChildren().add(button);
            }
        }
        buffBan();
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
                matrixFlowPane.getChildren().get(X * 12 + Y).setStyle("-fx-background-color: transparent; -fx-font-size: 15px; -fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                getHint(event);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pairSet = new HashSet<>();
        hintButton.setCursor(Cursor.HAND);
    }
}
