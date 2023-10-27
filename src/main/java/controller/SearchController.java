package controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

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

    public SearchTextController getSearchTextController() {
        return searchTextController;
    }

    public SearchWordController getSearchWordController() {
        return searchWordController;
    }

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
        searchWordController.init(sideBarController.getHistoryManager());
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
