package controller;

import database.UserDB;
import help.Help;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.BookMarkManager;
import model.HistoryManager;
import model.ScreenManager;
import model.WordManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController extends Controller implements Initializable {
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
    void changeToLogin(MouseEvent event) throws IOException {
        ScreenManager.getInstance().setScreen("LogIn");
    }

    @FXML
    void logout(MouseEvent event) {
        Help.threadProcess(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                BookMarkManager.getInstance().saveDatabase();
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
        ScreenManager.getInstance().setScreen("SearchWord");
    }

    @FXML
    void changeToBookmark(MouseEvent event) throws IOException {
        ScreenManager.getInstance().setScreen("BookMark");
    }

    @FXML
    void changeToAddword(MouseEvent event) throws IOException {
        ScreenManager.getInstance().setScreen("AddWord");
    }

    @FXML
    void changeToSearchText(MouseEvent event) throws IOException {
        ScreenManager.getInstance().setScreen("TranslateText");
    }

    @FXML
    void learnANewWord(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        sideBarController.searchWord(WordManager.getWordManager().getRandomWord().getSpelling());
    }

    public void updateHistoryList() throws IOException {
        historyListView.getItems().clear();
        historyListView.getItems().addAll(HistoryManager.getInstance().getWordSpelling());
    }

    public void updateBookmarkList() throws IOException {
        bookmarkListView.getItems().clear();
        bookmarkListView.getItems().addAll(BookMarkManager.getInstance().getWordSpelling());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            historyListView = new ListView<>();
            bookmarkListView = new ListView<>();
            updateHistoryList();
            updateBookmarkList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        historyScrollPane.setContent(historyListView);
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
