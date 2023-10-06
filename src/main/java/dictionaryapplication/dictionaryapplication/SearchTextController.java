package dictionaryapplication.dictionaryapplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchTextController extends CommonController implements Initializable {
    private SearchController searchController;

    @FXML
    private ImageView back;

    @FXML
    private Button engToVieButton;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea outpurTextArea;

    @FXML
    private Button vieToEngButton;

    public void init(SearchController searchController) {
        this.searchController = searchController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnMouseClicked(mouseEvent -> {
            try {
                searchController.getSideBarController().changeToMainSearch(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        vieToEngButton.setCursor(Cursor.HAND);
        outpurTextArea.setCursor(Cursor.TEXT);
        inputTextArea.setCursor(Cursor.TEXT);
        engToVieButton.setCursor(Cursor.HAND);
        back.setCursor(Cursor.HAND);
    }
}
