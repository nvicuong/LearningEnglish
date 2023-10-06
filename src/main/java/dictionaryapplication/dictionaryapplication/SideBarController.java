package dictionaryapplication.dictionaryapplication;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SideBarController extends CommonController implements Initializable {

    private Parent homeParent;
    private Parent searchMainParent;

    public Parent getBookMarkParent() {
        return bookMarkParent;
    }

    private Parent bookMarkParent;
    private Parent helpParent;

    private HomeController homeController;


    private SearchController searchController;


    private BookMarkController bookMarkController;
    private HelpController helpController;


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

    @Override
    public void loadPage(Parent root) throws IOException {
        borderPane.setCenter(root);
    }

    /** Side Bar Transiting. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
            loadPage(homeParent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //slide menu bar
        menu.setVisible(true);
        slider.setTranslateX(0);
        menu.setOnMouseClicked(event -> {
            if (slider.getTranslateX() == -176) {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);
                slide.setToX(0);
                slide.play();

                slider.setTranslateX(-176);

            } else {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(slider);

                slide.setToX(-176);
                slide.play();

                slider.setTranslateX(0);
            }
        });

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
        searchTextField.setCursor(Cursor.TEXT);

    }
}
