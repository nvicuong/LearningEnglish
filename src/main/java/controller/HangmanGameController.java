package controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.VoiceRssAPI;
import model.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class HangmanGameController extends Controller implements Initializable {

    private BookMarkController bookMarkController;

    @FXML
    private Text correctAnswerText;

    @FXML
    private Button correctButton;

    @FXML
    private Button replayButton;

    @FXML
    private ImageView makeSoundImageView;

    @FXML
    private ImageView back;

    @FXML
    private ImageView nextButton;

    @FXML
    private ImageView previousButton;

    @FXML
    private Text definitionText;

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

    @FXML
    FlowPane flowPaneTarget = new FlowPane();

    private List<Word> wordList;
    private int index;
    private int mistakes;
    private String myWord;
    private List<Character> list = new ArrayList<>();

    private String res;

    public void init(BookMarkController bookMarkController) {
        this.bookMarkController = bookMarkController;
    }


    @FXML
    void changeToNextWord(MouseEvent event) {
        index++;
        if (index < wordList.size() && !wordList.isEmpty()) {
            setContent(wordList.get(index));
        } else {
            index--;
        }
        checkIsEmpty();
    }

    @FXML
    void changeToPreviousWord(MouseEvent event) {
        index--;
        if (index >= 0 && !wordList.isEmpty()) {
            setContent(wordList.get(index));
        } else {
            index++;
        }
        checkIsEmpty();
    }

    @FXML
    void backtoBookmark(MouseEvent event) throws IOException {
        bookMarkController.getSideBarController().changeToBookmark(event);
    }

    @FXML
    void makeSoundWord(MouseEvent event) throws Exception {
        VoiceRssAPI.speakWord(myWord);
    }

    @FXML
    void replay(MouseEvent event) {
        mistakes = 0;
        base.setVisible(false);
        base1.setVisible(false);
        base2.setVisible(false);
        hand.setVisible(false);
        foot.setVisible(false);
        foot1.setVisible(false);
        head.setVisible(false);
        man.setVisible(false);
        index = 0;
        setContent(wordList.get(0));
        checkIsEmpty();
        replayButton.setVisible(false);
        flowPaneOrigin.setDisable(false);
    }

    public void setContent(Word word) {
        res = "";
        correctAnswerText.setVisible(false);
        correctButton.setVisible(false);
        replayButton.setVisible(false);
        myWord = word.getSpelling();
        System.out.println(myWord);
        list.clear();
        String[] lines = word.getContent().split("\n");
        definitionText.setText(lines[0]);
        for (int i = 0; i < myWord.length(); i++) {
            list.add(myWord.charAt(i));
        }
        Collections.shuffle(list);
        setButtonList();
    }


    private void transisionTo(Button button, int x) {
        button.setOnMouseClicked(event -> {
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
        flowPaneOrigin.getChildren().clear();
        flowPaneTarget.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {
            char ch = list.get(i);
            Button button = new Button(String.valueOf(ch).toUpperCase());
            button.setMinWidth(40);
            button.setMinHeight(40);
            button.setStyle("-fx-background-color: #3580BB; -fx-text-fill: #FFFFFF;");
            button.setCursor(Cursor.HAND);
            transisionTo(button, i);
            flowPaneOrigin.getChildren().add(button);
        }
    }


    public void checkWin() {
        if (res.equals(myWord)) {
            if (nextButton.isVisible()) {
                correctButton.setVisible(true);
            } else {
                replayButton.setVisible(true);
            }
        }

    }

    public void checkIsEmpty() {
        nextButton.setVisible((index != wordList.size() - 1) && (!wordList.isEmpty()));
        previousButton.setVisible(index > 0);
    }

    public void start(List<Word> list) {
        index = 0;
        wordList = new ArrayList<>(list);
        Collections.shuffle(wordList);
        if (!wordList.isEmpty()) {
            setContent(wordList.get(0));
        }
        checkIsEmpty();
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
            replayButton.setVisible(true);
            correctAnswerText.setText("Correct answer: " + myWord);
            correctAnswerText.setVisible(true);
        }
    }


    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        res = "";
        index = 0;
        mistakes = 0;
        base.setVisible(false);
        base1.setVisible(false);
        base2.setVisible(false);
        head.setVisible(false);
        foot.setVisible(false);
        foot1.setVisible(false);
        hand.setVisible(false);
        man.setVisible(false);
        flowPaneOrigin.setAlignment(Pos.CENTER);
        back.setCursor(Cursor.HAND);
        nextButton.setCursor(Cursor.HAND);
        previousButton.setCursor(Cursor.HAND);
        makeSoundImageView.setCursor(Cursor.HAND);
        replayButton.setCursor(Cursor.HAND);
        correctButton.setCursor(Cursor.HAND);

    }
}
