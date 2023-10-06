package dictionaryapplication.dictionaryapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SearchController extends CommonController implements Initializable {

    private SideBarController sideBarController;

    private Parent searchWordParent;
    private Parent searchTextParent;

    private SearchTextController searchTextController;
    private SearchWordController searchWordController;

    @FXML
    private Button searchTextButton;

    @FXML
    private Button searchWordButton;


    public SideBarController getSideBarController() {
        return sideBarController;
    }


    @FXML
    public void changeToSearchWord(MouseEvent event) throws IOException {
        loadPage(searchWordParent);
    }

    @FXML
    public void changeToTranslateText(MouseEvent event) throws IOException {
        loadPage(searchTextParent);
    }

    public void init(SideBarController sideBarController) {
        this.sideBarController = sideBarController;
    }

    @Override
    public void loadPage(Parent parent) throws IOException {
        sideBarController.getBorderPane().setCenter(parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchWord.fxml"));
            searchWordParent = loader.load();
            searchWordController = loader.getController();
            searchWordController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchText.fxml"));
            searchTextParent = loader.load();
            searchTextController = loader.getController();
            searchTextController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        searchTextButton.setCursor(Cursor.HAND);
        searchWordButton.setCursor(Cursor.HAND);
    }
}
