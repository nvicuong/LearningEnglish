package controller;

import database.UserDB;
import help.Help;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.BookMarkManager;
import model.HistoryManager;
import model.WordManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController extends Controller implements Initializable {

    public Parent getLogInParent() {
        return logInParent;
    }

    private Parent logInParent;

    public LogInController getLogInController() {
        return logInController;
    }

    private LogInController logInController;


    private SideBarController sideBarController;

    public AnchorPane getHomeAnchorPane() {
        return homeAnchorPane;
    }

    @FXML
    private AnchorPane homeAnchorPane;

    @FXML
    private Label welcomeLabel;

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

    @FXML
    private ListView<String> historyListView;

    @FXML
    private ScrollPane historyScrollPane;

    @FXML
    private ListView<String> bookmarkListView;

    @FXML
    private ScrollPane bookmarkScrollPane;

    @FXML
    private TitledPane historyTitledPane;


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
    void logout(MouseEvent event) {
        Help.threadProcess(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                BookMarkManager.getBookMarkManager().saveDatabase();
                return null;
            }

            @Override
            protected void succeeded() {
                UserDB.logout();
                resetUser();
                super.succeeded();
            }
        }, homeAnchorPane, "SAVING...");
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

    @FXML
    void learnANewWord(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        sideBarController.searchWord(WordManager.getWordManager().getRandomWord().getSpelling());
    }

    @Override
    public void loadPage(Parent parent) throws IOException {
        sideBarController.loadPage(parent);
    }

    public void updateHistoryList() throws IOException {
        historyListView.getItems().clear();
        historyListView.getItems().addAll(HistoryManager.getHistoryManager().getWordSpelling());
    }

    public void updateBookmarkList() throws IOException {
        bookmarkListView.getItems().clear();
        bookmarkListView.getItems().addAll(BookMarkManager.getBookMarkManager().getWordSpelling());
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


        historyListView = new ListView<>();
        historyScrollPane.setContent(historyListView);

        bookmarkListView = new ListView<>();
        bookmarkScrollPane.setContent(bookmarkListView);


        addWordButton.setCursor(Cursor.HAND);
        learnNewWordButton.setCursor(Cursor.HAND);
        loginButton.setCursor(Cursor.HAND);
        logoutButton.setCursor(Cursor.HAND);
        removeWordButton.setCursor(Cursor.HAND);
        searchInBankButton.setCursor(Cursor.HAND);
        searchNewWordButton.setCursor(Cursor.HAND);
        translateButton.setCursor(Cursor.HAND);

        resetUser();
    }

    public void resetUser() {
        if (UserDB.getUsername() != null) {
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
            welcomeLabel.setText("Welcome back, " + UserDB.getUsername() + "!");
        } else {
            loginButton.setVisible(true);
            logoutButton.setVisible(false);
            welcomeLabel.setText("Not logged in!");
        }
    }
}
