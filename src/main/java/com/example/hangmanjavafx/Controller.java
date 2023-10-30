package com.example.hangmanjavafx;


import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private FlowPane buttons;

    @FXML
    private Pane man;

    @FXML
    private Pane foot;

    @FXML
    private Text realWord;

    @FXML
    private Pane foot1;

    @FXML
    private Pane hand;

    @FXML
    private Pane head;

    @FXML
    private Text text;

    @FXML
    private Text winStatus;

    private boolean wordStatus = false;
    private int mistakes;
    private int correct;
    private Words word = new Words();
    private String myWord;
    private List<Character> list = new ArrayList<>();
    private List<String> myLetters;
    private List<String> answer;

    ArrayList<ArrayList<Integer>> pos = new ArrayList<>(26);

    public Controller() throws FileNotFoundException
    {
    }


    public void initialize() {
        interView.setVisible(true);
        base.setVisible(false);
        base1.setVisible(false);
        base2.setVisible(false);
        head.setVisible(false);
        foot.setVisible(false);
        foot1.setVisible(false);
        hand.setVisible(false);
        man.setVisible(false);
        mistakes=0;
        correct=0;
        myWord = word.getRandomWord();
        for (int i = 0; i < myWord.length(); i++) {
            list.add(myWord.charAt(i));
        }
        Collections.shuffle(list);
        System.out.println(list);
        myWord = myWord.toUpperCase();
        myLetters = Arrays.asList(myWord.split(""));
        System.out.println(myLetters);
        answer = Arrays.asList(new String[myLetters.size()*2]);
        for(int i=0; i< myLetters.size()*2; i++){
            if(i%2==0){
                answer.set(i, "_");
            }else{
                answer.set(i, " ");
            }
        }
        String res = String.join("", answer);
        text.setText(res);
        winStatus.setText("");
        realWord.setText("");
        buttons.setDisable(false);
    }

    @FXML
    public void onClick(MouseEvent event) {
        String letter = ((Button)event.getSource()).getText();
        ((Button) event.getSource()).setDisable(true);
        for (int i = 0; i < 26; i++) {
            pos.add(new ArrayList<>());
        }

        if(myLetters.contains(letter)){
            int letterIndex;
            while ((letterIndex = myLetters.indexOf(letter)) != -1) {
                correct++;
                myLetters.set(letterIndex, ".");
                answer.set(letterIndex * 2, letter);
                String res = String.join("", answer);
                text.setText(res);
            }
            if(correct==myWord.length()){
                winStatus.setText("You Win!");
                buttons.setDisable(true);
            }
        }else{
            mistakes++;
            if(mistakes == 1) base.setVisible(true);
            else if (mistakes ==2) base1.setVisible(true);
            else if (mistakes ==3) base2.setVisible(true);
            else if (mistakes ==4) head.setVisible(true);
            else if (mistakes ==5) foot.setVisible(true);
            else if (mistakes ==6) foot1.setVisible(true);
            else if (mistakes ==7) hand.setVisible(true);
            else if (mistakes ==8){
                hand.setVisible(false);
                man.setVisible(true);
                winStatus.setText("You Lose!");
                realWord.setText("The actual word was " + myWord);
                buttons.setDisable(true);
            }
        }
    }

    @FXML
    public void newGame(){
        wordStatus = false;
        for(int i=0; i<27; i++){
            buttons.getChildren().get(i).setDisable(false);
        }
        initialize();
    }


    @FXML
    public void randomChange(MouseEvent event) {
        wordStatus = true;
        System.out.println("xoa het tu khong co trong myWord");
        for(int i=0; i<27; i++){
            buttons.getChildren().get(i).setDisable(true);
        }
        for(int i=0; i < myWord.length(); i++){
            buttons.getChildren().get(myWord.charAt(i) - 'A').setDisable(false);
        }
    }


    @FXML
    ImageView myImageView;
    Image myImage = new Image(String.valueOf(getClass().getResource("/data/background.jpg")));
    public void displayImage() {
        myImageView.setImage(myImage);
    }

}
