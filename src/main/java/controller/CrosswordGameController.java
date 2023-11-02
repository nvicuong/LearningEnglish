package controller;

import games.RunCrosswordGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CrosswordGameController implements Initializable {
    @FXML
    private FlowPane matrixFlowPane;

    public void loadMatrix() {
        matrixFlowPane.getChildren().clear();
        matrixFlowPane.setPrefWidth(RunCrosswordGame.getRunCrosswordGame().getMatrix().size() * (35 + matrixFlowPane.getVgap()) + 25);
        for (ArrayList<Character> arr : RunCrosswordGame.getRunCrosswordGame().getMatrix()) {
            for (Character c : arr) {
                Button button = new Button();
                button.setMinWidth(35);
                button.setMinHeight(35);
                button.setText(String.valueOf(c).toUpperCase());
                button.setStyle("-fx-background-color: transparent; -fx-font-size: 15px;");
                matrixFlowPane.getChildren().add(button);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
