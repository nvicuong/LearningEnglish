package controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import model.ScreenManager;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SearchController extends Controller implements Initializable {
    @FXML
    private Button searchTextButton;

    @FXML
    private Button searchWordButton;

    @FXML
    public void changeToSearchWord(MouseEvent event) throws IOException {
        ScreenManager.getInstance().setScreen("SearchWord");
    }

    @FXML
    public void changeToTranslateText(MouseEvent event) throws IOException {
        ScreenManager.getInstance().setScreen("SearchText");
    }

    @Override
    public void init() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextButton.setCursor(Cursor.HAND);
        searchWordButton.setCursor(Cursor.HAND);
    }
}
