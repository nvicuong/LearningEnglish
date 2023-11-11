package controller;

import javafx.scene.Parent;
import model.BookMarkManager;
import model.VoiceRssAPI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Word;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowWordController extends Controller implements Initializable {

    private BookMarkController bookMarkController;

    @FXML
    private Button nextWordButton;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private Label pronunciationLabel;

    @FXML
    private Button saveButton;

    @FXML
    private ImageView makeSoundImageView;

    @FXML
    private Label spellingLabel;

    @FXML
    private Label synonymLabel;

    @FXML
    void makeSoundWord(MouseEvent event) throws Exception {
        VoiceRssAPI.speakWord(spellingLabel.getText());
    }

    @FXML
    void nextWord(MouseEvent event) throws SQLException, IOException, ClassNotFoundException {
        bookMarkController.getSideBarController().getHomeController().learnANewWord(event);
    }

    @FXML
    void saveWord(MouseEvent event) throws IOException {
        BookMarkManager.getBookMarkManager().addWord(new Word(spellingLabel.getText(), pronunciationLabel.getText(), contentTextArea.getText(), synonymLabel.getText()));
        bookMarkController.updateWord();
        bookMarkController.getSideBarController().getHomeController().updateBookmarkList();
    }

    @Override
    public void loadPage(Parent parent) throws IOException {
        bookMarkController.getSideBarController().loadPage(parent);
    }

    public void setContent(Word word) {
        spellingLabel.setText(word.getSpelling());
        pronunciationLabel.setText(word.getPronunciation());
        synonymLabel.setText(word.getSynonym());
        contentTextArea.setText(word.getContent());
    }

    public void init(BookMarkController bookMarkController) {
        this.bookMarkController = bookMarkController;
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        makeSoundImageView.setCursor(Cursor.HAND);
        saveButton.setCursor(Cursor.HAND);
        nextWordButton.setCursor(Cursor.HAND);
    }
}
