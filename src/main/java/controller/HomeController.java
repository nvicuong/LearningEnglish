package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends CommonController implements Initializable {

    private Parent logInParent;
    private LogInController logInController;


    private SideBarController sideBarController;


    @FXML
    private Label addWordButton;

    @FXML
    private Label learnNewWordButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label removeWordButton;

    @FXML
    private Label searchInBankButton;

    @FXML
    private Label searchNewWordButton;

    @FXML
    private Label translateButton;

    public SideBarController getSideBarController() {
        return sideBarController;
    }

    public void init(SideBarController sideBarController) {
        this.sideBarController = sideBarController;
    }
    @FXML
    void changeToLogin(MouseEvent event) throws IOException {
        loadPage(logInParent);
    }

    @FXML
    void changeToSearchWord(MouseEvent event) throws IOException {
        sideBarController.getSearchController().changeToSearchWord(event);
    }

    @FXML
    void changeToBookmark(MouseEvent event) throws IOException {
        sideBarController.changeToBookmark(event);
    }

    @FXML
    void changeToAddword(MouseEvent event) throws IOException {
        sideBarController.getBookMarkController().changeToAddWord(event);
    }

    @FXML
    void changeToSearchText(MouseEvent event) throws IOException {
        sideBarController.getSearchController().changeToTranslateText(event);
    }



    @Override
    public void loadPage(Parent parent) throws IOException {
        sideBarController.getBorderPane().setCenter(parent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            logInParent = loader.load();
            logInController = loader.getController();
            logInController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addWordButton.setCursor(Cursor.HAND);
        learnNewWordButton.setCursor(Cursor.HAND);
        loginButton.setCursor(Cursor.HAND);
        logoutButton.setCursor(Cursor.HAND);
        removeWordButton.setCursor(Cursor.HAND);
        searchInBankButton.setCursor(Cursor.HAND);
        searchNewWordButton.setCursor(Cursor.HAND);
        translateButton.setCursor(Cursor.HAND);


    }
}
