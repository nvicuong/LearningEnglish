package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.ScreenManager;
import model.VoiceRssAPI;
import model.translateAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SearchTextController extends Controller implements Initializable {
    @FXML
    private ImageView back;

    @FXML
    private ImageView makeSoundImageView;

    @FXML
    private Button engToVieButton;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button vieToEngButton;

    @FXML
    void translateTextEnToVi(MouseEvent event) throws IOException {
        String text = inputTextArea.getText();
        translateAPI.translateTextEntoVi(text);
        loadOutputText();
    }

    @FXML
    void translateTextVitoEn(MouseEvent event) throws IOException {
        String text = inputTextArea.getText();
        translateAPI.translateTextViToEn(text);
        loadOutputText();
    }

    @FXML
    void makeSoundWord(MouseEvent event) throws Exception {
        VoiceRssAPI.speakWord(inputTextArea.getText());
    }

    public void loadOutputText() throws FileNotFoundException {
        File file = new File("src\\\\main\\\\resources\\\\data\\\\translateText.txt");
        Scanner scanner = new Scanner(file);
        String s = scanner.nextLine();
        s = s.substring(44, s.length() - 5);
        outputTextArea.setText(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnMouseClicked(mouseEvent -> {
            try {
                ScreenManager.getInstance().setScreen("Search");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        vieToEngButton.setCursor(Cursor.HAND);
        outputTextArea.setCursor(Cursor.TEXT);
        inputTextArea.setCursor(Cursor.TEXT);
        engToVieButton.setCursor(Cursor.HAND);
        makeSoundImageView.setCursor(Cursor.HAND);
        back.setCursor(Cursor.HAND);
    }

    /**
     *
     */
    @Override
    public void init() {
    }
}
