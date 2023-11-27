package controller;

import help.Help;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import model.BookMarkManager;
import model.ScreenManager;
import model.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddWordController extends Controller implements Initializable {

    private BookMarkController bookMarkController;
    private HomeController homeController;

    @FXML
    private Button addButton;

    @FXML
    private ImageView back;

    @FXML
    private Button cancelWordButton;

    @FXML
    private TextArea definitionTextArea;

    @FXML
    private TextField pronunciationTextField;

    @FXML
    private Line wordLine;

    @FXML
    private TextField wordTextField;


    @FXML
    void addNewWord(MouseEvent event) throws IOException {
        if (wordTextField.getText().isEmpty() || wordTextField.getText().isBlank() || definitionTextArea.getText().isEmpty() || definitionTextArea.getText().isBlank()) {
            Help.showNotification("WARNING", "word field and definition field must be not blank!");
        } else {
            Word word = new Word(wordTextField.getText(), pronunciationTextField.getText(), definitionTextArea.getText(), "");
            BookMarkManager.getInstance().addWord(word);
            bookMarkController.updateWord();
            homeController.updateBookmarkList();
            Help.showNotification("NOTIFICATION", "Add word successfully!");
        }
    }

    @FXML
    void cancelWord(MouseEvent event) {
        clearContent();
    }

    @FXML
    void changeToBookMark(MouseEvent event) throws IOException {
        ScreenManager.getInstance().setScreen("BookMark");
    }

    public void clearContent() {
        wordTextField.clear();
        definitionTextArea.clear();
        pronunciationTextField.clear();
    }

    public void setContent(Word word) {
        wordTextField.setText(word.getSpelling());
        pronunciationTextField.setText(word.getPronunciation());
        definitionTextArea.setText(word.getContent());
    }

    /**
     *
     */
    @Override
    public void init() {
        this.homeController = (HomeController) ScreenManager.getInstance().getController("Home");
        this.bookMarkController = (BookMarkController) ScreenManager.getInstance().getController("BookMark");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnMouseClicked(mouseEvent -> {
            try {
                ScreenManager.getInstance().setScreen("BookMark");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        addButton.setCursor(Cursor.HAND);
        back.setCursor(Cursor.HAND);
        cancelWordButton.setCursor(Cursor.HAND);
        definitionTextArea.setCursor(Cursor.TEXT);
        pronunciationTextField.setCursor(Cursor.TEXT);
        wordTextField.setCursor(Cursor.TEXT);
    }
}
