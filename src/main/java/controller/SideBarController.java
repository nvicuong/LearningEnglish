package controller;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerLight;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import model.HistoryManager;
import model.Word;
import model.WordManager;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class SideBarController extends CommonController implements Initializable {
    private Parent homeParent;
    private Parent searchMainParent;

    public Parent getBookMarkParent() {
        return bookMarkParent;
    }

    private Parent bookMarkParent;
    private Parent helpParent;

    private Parent showWordParent;


    private HomeController homeController;


    private SearchController searchController;
    private ShowWordController showWordController;


    private BookMarkController bookMarkController;
    private HelpController helpController;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private ListView<String> searchListView;



    ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    private ToggleButton modeButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button bookMarkLeftButton;

    @FXML
    private Button bookMarkUpButton;

    @FXML
    private Button helpLeftButton;

    @FXML
    private Button helpUpbutton;

    @FXML
    private Button homeLeftButton;

    @FXML
    private Button homeUpButton;

    @FXML
    private Label menu;

    @FXML
    private ImageView searchImageView;

    @FXML
    private Button searchLeftButton;


    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchUpButton;

    @FXML
    private AnchorPane slider;

    public HomeController getHomeController() {
        return homeController;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public SearchController getSearchController() {
        return searchController;
    }

    public BookMarkController getBookMarkController() {
        return bookMarkController;
    }

    @FXML
    public void changeToHome(MouseEvent event) throws IOException {
        loadPage(homeParent);
    }

    @FXML
    public void changeToMainSearch(MouseEvent event) throws IOException {
        loadPage(searchMainParent);
    }

    @FXML
    public void changeToBookmark(MouseEvent event) throws IOException {
        loadPage(bookMarkParent);
    }

    @FXML
    public void changeToHelp(MouseEvent event) throws IOException {
        loadPage(helpParent);
    }

    @FXML
    void searchWordButton(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (searchListView.getSelectionModel().getSelectedItem() == null) {
            searchWord(searchTextField.getText());
        } else {
            searchWord(searchListView.getSelectionModel().getSelectedItem());
        }
    }

    public void changeToShowWord() throws IOException {
        loadPage(showWordParent);
    }

    public void changeToShowWord(Word word) throws IOException {
        showWordController.setContent(word);
        loadPage(showWordParent);
    }

    public void searchWord(String s) throws SQLException, IOException, ClassNotFoundException {
    int index = WordManager.getWordManager().binarySearchWordOnly(s);
        if (index >= 0) {
            Word word = WordManager.getWordManager().getEngWordIndex(index);
            HistoryManager.getHistoryManager().addWordToHistory(word);
            homeController.updateHistoryList();
            showWordController.setContent(word);
            searchListView.setVisible(false);
            changeToShowWord();
            searchController.getSearchWordController().updateWord();
        } else {
            showNotification("Notification", "word is not valid!");
        }
    }

    @Override
    public void loadPage(Parent root) throws IOException {
        borderPane.setCenter(root);
        checkSlide();
    }


    /**
     * Side Bar Transiting.
     */

    public void checkSlide() {
        if (slider.getTranslateX() == 0) {
            borderPane.getCenter().setTranslateX(0);
        } else {
            borderPane.getCenter().setTranslateX(-100);
        }
    }

    public void searchBarViewControl() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            items.clear();
            searchListView.setVisible(!newValue.isEmpty());
            List<String> s = null;
            try {
                s = WordManager.getWordManager().searchWordList(newValue);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            items.addAll(s);
            int size = searchListView.getItems().size();
            if (size < 10) {
                searchListView.setPrefHeight(size * 25);
            } else {
                searchListView.setPrefHeight(250);
            }
        });
        searchListView.setItems(items);

        searchListView.setOnMouseClicked(event -> {
            if (searchListView.getSelectionModel().getSelectedItem() != null) {
                try {
                    searchWord(searchListView.getSelectionModel().getSelectedItem());
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            });


            searchTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER && !searchListView.getItems().isEmpty()) {
                    try {
                        searchWord(searchTextField.getText());
                    } catch (SQLException | IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                    showNotification("Notification", "word is not valid!");
                }
                if (Objects.requireNonNull(event.getCode()) == KeyCode.DOWN && !searchListView.getItems().isEmpty()) {
                    searchListView.requestFocus();
                }
            }
        });

        searchListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                    if (searchListView.getSelectionModel().getSelectedItem() == null) {
                        try {
                            searchWord(searchTextField.getText());
                        } catch (SQLException | IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            searchWord(searchListView.getSelectionModel().getSelectedItem());
                        } catch (SQLException | IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (Objects.requireNonNull(event.getCode()) == KeyCode.UP
                        && Objects.equals(searchListView.getSelectionModel().getSelectedItem(), searchListView.getItems().get(0))) {
                    searchTextField.requestFocus();
                    searchTextField.positionCaret(searchTextField.getText().length());
                }
            }
        });

    }

    public void slideMenuBar() {
        menu.setVisible(true);
        slider.setTranslateX(0);
        menu.setOnMouseClicked(event -> {
            if (slider.getTranslateX() == -176) {
                TranslateTransition slide = new TranslateTransition();
                TranslateTransition slide1 = new TranslateTransition();

                slide.setDuration(Duration.seconds(0.4));
                slide1.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);
                slide1.setNode(borderPane.getCenter());
                slide.setToX(0);
                slide1.setToX(0);
                slide.play();
                slide1.play();
                borderPane.getCenter().setTranslateX(-80);
                slider.setTranslateX(-176);

            } else {
                TranslateTransition slide = new TranslateTransition();
                TranslateTransition slide1 = new TranslateTransition();


                slide.setNode(slider);
                slide1.setNode(borderPane.getCenter());

                slide.setDuration(Duration.seconds(0.4));
                slide1.setDuration(Duration.seconds(0.4));

                slide.setToX(-176);
                slide1.setToX(-80);

                slide.play();
                slide1.play();

                slider.setTranslateX(0);
                borderPane.getCenter().setTranslateX(0);
            }
        });
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modeButton.setSelected(false);
        Application.setUserAgentStylesheet(null);
        modeButton.setText("DARKMODE");
        modeButton.setOnAction(event -> {
            if (modeButton.isSelected()) {
                modeButton.setText("LIGHTMODE");
                Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
            } else {
                modeButton.setText("DARKMODE");
                Application.setUserAgentStylesheet(null);
            }
        });

        //search
        searchListView.setVisible(false);
        searchBarViewControl();


        //set mouse to move the searchTextField and searchListView
        mainAnchorPane.setOnMouseClicked(mouseEvent -> {
            searchUpButton.requestFocus();
            searchListView.setVisible(false);
        });

        //loadPage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            homeParent = loader.load();
            homeController = loader.getController();
            homeController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Search.fxml"));
            searchMainParent = loader.load();
            searchController = loader.getController();
            searchController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
            helpParent = loader.load();
            helpController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Bookmark.fxml"));
            bookMarkParent = loader.load();
            bookMarkController = loader.getController();
            bookMarkController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowWord.fxml"));
            showWordParent = loader.load();
            showWordController = loader.getController();
            showWordController.init(bookMarkController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            loadPage(homeParent);
            homeController.updateHistoryList();
            homeController.updateBookmarkList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //slide menu bar
        slideMenuBar();

        menu.setCursor(Cursor.HAND);
        homeLeftButton.setCursor(Cursor.HAND);
        homeUpButton.setCursor(Cursor.HAND);
        searchLeftButton.setCursor(Cursor.HAND);
        searchUpButton.setCursor(Cursor.HAND);
        bookMarkLeftButton.setCursor(Cursor.HAND);
        bookMarkUpButton.setCursor(Cursor.HAND);
        helpLeftButton.setCursor(Cursor.HAND);
        helpUpbutton.setCursor(Cursor.HAND);
        searchImageView.setCursor(Cursor.HAND);
        modeButton.setCursor(Cursor.HAND);
        searchTextField.setCursor(Cursor.TEXT);

    }
}
