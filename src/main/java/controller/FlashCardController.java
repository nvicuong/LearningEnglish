package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.ScreenManager;
import model.VoiceRssAPI;
import model.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class FlashCardController extends Controller implements Initializable {
    private List<Word> wordList;
    private int index;

    @FXML
    private ImageView back;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private ImageView makeSoundImageView;

    @FXML
    private ImageView nextButton;

    @FXML
    private ImageView previousButton;

    @FXML
    private Label pronunciationLabel;

    @FXML
    private Label spellingLabel;

    @FXML
    private Label synonymLabel;

    @FXML
    private Label positionInListLabel;

    @FXML
    private Button spellingViewButton;

    @FXML
    void changeToNextWord(MouseEvent event) {
        index++;
        if (index < wordList.size() && !wordList.isEmpty()) {
            spellingViewButton.setVisible(true);
            setContent(wordList.get(index));
        } else {
            index--;
        }
        System.out.println(index);
        checkIsEmpty();
    }

    public void checkIsEmpty(){
        nextButton.setVisible((index != wordList.size() - 1) && (!wordList.isEmpty()));
        previousButton.setVisible(index > 0);
    }

    @FXML
    void changeToPreviousWord(MouseEvent event) {
        index--;
        if (index >= 0 && !wordList.isEmpty()) {
            spellingViewButton.setVisible(true);
            setContent(wordList.get(index));
        } else {
            index++;
        }
        checkIsEmpty();
    }

    @FXML
    void flipCard(MouseEvent event) {
        spellingViewButton.setVisible(false);
    }

    @FXML
    void makeSoundWord(MouseEvent event) throws Exception {
        VoiceRssAPI.speakWord(spellingLabel.getText());
    }


    public void setContent(Word word) {
        spellingLabel.setText(word.getSpelling());
        pronunciationLabel.setText(word.getPronunciation());
        synonymLabel.setText(word.getSynonym());
        contentTextArea.setText(word.getContent());
        spellingViewButton.setText(word.getSpelling());
        positionInListLabel.setText(String.valueOf(index + 1) + "/" + String.valueOf(wordList.size()));
    }

    public void start(List<Word> list) {
        index = 0;
        wordList = new ArrayList<>(list);
        Collections.shuffle(wordList);
        if (!wordList.isEmpty()) {
            setContent(wordList.get(0));
        } else {
            setContent(new Word("is empty :(", "tin chuan chua a", "anh song nhu the day em", ""));
            positionInListLabel.setText("0/0");
        }
        spellingViewButton.setVisible(true);
        checkIsEmpty();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        index = 0;
        back.setOnMouseClicked(mouseEvent -> {
            try {
                ScreenManager.getInstance().setScreen("BookMark");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        back.setCursor(Cursor.HAND);
        spellingViewButton.setCursor(Cursor.HAND);
        nextButton.setCursor(Cursor.HAND);
        previousButton.setCursor(Cursor.HAND);
        makeSoundImageView.setCursor(Cursor.HAND);
    }
}
