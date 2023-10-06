package dictionaryapplication.dictionaryapplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchWordController extends CommonController implements Initializable {
    private SearchController searchController;

    @FXML
    private ImageView back;

    @FXML
    private TableView<?> historyTableView;

    @FXML
    private Button searchWordButton;

    @FXML
    private TextField searchWordTextField;

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

        back.setCursor(Cursor.HAND);
        searchWordButton.setCursor(Cursor.HAND);
        searchWordTextField.setCursor(Cursor.TEXT);
    }
}
