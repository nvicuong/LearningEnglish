package dictionaryapplication.dictionaryapplication;

import javafx.event.ActionEvent;
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

        searchNewWordButton.setOnMouseClicked(mouseEvent -> {
            try {
                sideBarController.getSearchController().changeToSearchWord(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        searchInBankButton.setOnMouseClicked(mouseEvent -> {
            try {
                sideBarController.changeToBookmark(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException("loi o day", e);
            }
        });

        addWordButton.setOnMouseClicked(mouseEvent -> {
            try {
                sideBarController.getBookMarkController().changeToAddWord(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        translateButton.setOnMouseClicked(mouseEvent -> {
            try {
                sideBarController.getSearchController().changeToTranslateText(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
