package dictionaryapplication.dictionaryapplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

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
    private TextField speechTextField;

    @FXML
    private Line wordLine;

    @FXML
    private TextField wordTextField;

    public void init(BookMarkController bookMarkController) {
        this.bookMarkController = bookMarkController;
    }

    @FXML
    void addNewWord(MouseEvent event) {

    }

    @FXML
    void cancelWord(MouseEvent event) {

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
        speechTextField.setCursor(Cursor.TEXT);
        wordTextField.setCursor(Cursor.TEXT);
    }
}
