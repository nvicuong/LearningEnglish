package controller;

import help.Help;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane mainAnchorPane;

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
        Help.threadProcess(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String text = inputTextArea.getText();
                outputTextArea.setText(translateAPI.translateTextEntoVi(text));
                return null;
            }
        }, mainAnchorPane, "Loading...");
    }

    @FXML
    void translateTextVitoEn(MouseEvent event) throws IOException {
        Help.threadProcess(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String text = inputTextArea.getText();
                outputTextArea.setText(translateAPI.translateTextViToEn(text));

                return null;
            }
        }, mainAnchorPane, "Loading...");
    }

    @FXML
    void makeSoundWord(MouseEvent event) throws Exception {
        Help.threadProcess(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                VoiceRssAPI.speakWord(inputTextArea.getText());
                return null;
            }
        }, mainAnchorPane, "Loading...");
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
}
