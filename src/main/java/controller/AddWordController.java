package controller;

import help.Help;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import model.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddWordController extends CommonController implements Initializable {

    private BookMarkController bookMarkController;


    @FXML
    private Button addButton;

    @FXML
    private ImageView back;

    @FXML
    private Button cancelWordButton;

    @FXML
    private Line definitionLine;

    @FXML
    private TextArea definitionTextArea;

    @FXML
    private Line speechLine;

    @FXML
    private TextField pronunciationTextField;

    @FXML
    private Line wordLine;

    @FXML
    private TextField wordTextField;

    public void init(BookMarkController bookMarkController) {
        this.bookMarkController = bookMarkController;
    }

    @FXML
    void addNewWord(MouseEvent event) throws IOException {
        if (wordTextField.getText().isEmpty() || wordTextField.getText().isBlank() || definitionTextArea.getText().isEmpty() || definitionTextArea.getText().isBlank()) {
            showNotification("WARNING", "word field and definition field must be not blank!");
        } else {
            Word word = Help.unformatWord(new Word(wordTextField.getText().toLowerCase(), pronunciationTextField.getText(), definitionTextArea.getText(), ""));
            bookMarkController.getBookMarkManager().addWordToBank(word);
            bookMarkController.updateWord();
            showNotification("NOTIFICATION", "Add word successfully!");
        }
    }

    @FXML
    void cancelWord(MouseEvent event) {
        wordTextField.clear();
        definitionTextArea.clear();
        pronunciationTextField.clear();
    }

    @FXML
    void changeToBookMark(MouseEvent event) {

    }

    @Override
    public void loadPage(Parent parent) throws IOException {
        bookMarkController.getSideBarController().getBorderPane().setCenter(parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnMouseClicked(mouseEvent -> {
            try {
                bookMarkController.getSideBarController().changeToBookmark(mouseEvent);
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
