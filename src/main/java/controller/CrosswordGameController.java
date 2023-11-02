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
        int count = 1;
        System.out.println(matrixFlowPane.getHgap());
        matrixFlowPane.setPrefWrapLength(12 * (30 + matrixFlowPane.getHgap()) + 20);
        for (ArrayList<Character> arr : RunCrosswordGame.getRunCrosswordGame().getMatrix()) {
            for (Character c : arr) {
                System.out.println(count);
                count++;
                Button button = new Button();
                button.setMinWidth(30);
                button.setMinHeight(30);
                button.setText(String.valueOf(c));
                matrixFlowPane.getChildren().add(button);
                System.out.println("tin chuan em nhe");
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
