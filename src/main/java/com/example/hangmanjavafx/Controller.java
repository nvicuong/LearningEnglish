package com.example.hangmanjavafx;


import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {

    @FXML
    private Pane interView;

    @FXML
    private Pane base;

    @FXML
    private Pane base1;

    @FXML
    private Pane base2;

    @FXML
    private FlowPane flowPaneOrigin;

    @FXML
    private Pane man;

    @FXML
    private Pane foot;


    @FXML
    private Pane foot1;

    @FXML
    private Pane hand;

    @FXML
    private Pane head;

    private boolean wordStatus = false;
    private int mistakes;
    private Words word = new Words();
    private String myWord;
    private List<Character> list = new ArrayList<>();

    private String res;
    ArrayList<ArrayList<Integer>> pos = new ArrayList<>(26);
    @FXML
    FlowPane flowPaneTarget = new FlowPane();


    private void transisionTo(Button button, int x) {
        button.setOnMouseClicked(event -> {
            System.out.println(res);
            if (flowPaneOrigin.getChildren().contains(button)) {
                flowPaneTarget.getChildren().add(button);
                res += button.getText().toLowerCase();
                if (myWord.indexOf(res) != 0) {
                    button.setStyle("-fx-background-color: #EB3324; -fx-text-fill: #FFFFFF;");
                    checkMistake();
                } else {
                    checkWin();
                    button.setStyle("-fx-background-color: #3580BB; -fx-text-fill: #FFFFFF;");
                }
                flowPaneOrigin.getChildren().remove(button);
                Button newButton = new Button();
                newButton.setMinWidth(40);
                newButton.setMinHeight(40);
                newButton.setVisible(false);
                flowPaneOrigin.getChildren().add(x, newButton);
            } else {
                if (flowPaneTarget.getChildren().indexOf(button) == flowPaneTarget.getChildren().size() - 1) {
                    flowPaneOrigin.getChildren().remove(x);
                    flowPaneOrigin.getChildren().add(x, button);
                    button.setStyle("-fx-background-color: #3580BB; -fx-text-fill: #FFFFFF;");
                    flowPaneTarget.getChildren().remove(button);
                    if (!res.isEmpty()) {
                        res = res.substring(0, res.length() - 1);
                    }
                }
            }
        });
    }

    private void setButtonList() {
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            char ch = list.get(i);
            Button button = new Button(String.valueOf(ch).toUpperCase());
            button.setMinWidth(40);
            button.setMinHeight(40);
            button.setLayoutX(50);
            button.setLayoutY(50);
            button.setStyle("-fx-background-color: #3580BB; -fx-text-fill: #FFFFFF;");
            transisionTo(button, i);
            flowPaneOrigin.getChildren().add(button);
        }
    }


    public void initialize() {
        res = "";
        interView.setVisible(true);
        base.setVisible(false);
        base1.setVisible(false);
        base2.setVisible(false);
        head.setVisible(false);
        foot.setVisible(false);
        foot1.setVisible(false);
        hand.setVisible(false);
        man.setVisible(false);
        mistakes = 0;
        flowPaneTarget.setLayoutX(50);
        flowPaneTarget.setLayoutY(50);

        myWord = word.getRandomWord();
        for (int i = 0; i < myWord.length(); i++) {
            list.add(myWord.charAt(i));
        }
        flowPaneOrigin.setAlignment(Pos.CENTER);
        System.out.println(myWord);
        Collections.shuffle(list);
        setButtonList();

    }

    public void checkWin() {
        if (res.equals(myWord)) {
            System.out.println("ngon");
        }
    }

    public void checkMistake() {
            mistakes++;
            if (mistakes == 1) base.setVisible(true);
            else if (mistakes == 2) base1.setVisible(true);
            else if (mistakes == 3) base2.setVisible(true);
            else if (mistakes == 4) head.setVisible(true);
            else if (mistakes == 5) foot.setVisible(true);
            else if (mistakes == 6) foot1.setVisible(true);
            else if (mistakes == 7) hand.setVisible(true);
            else if (mistakes == 8) {
                hand.setVisible(false);
                man.setVisible(true);
                flowPaneOrigin.setDisable(true);
        }
    }


}
