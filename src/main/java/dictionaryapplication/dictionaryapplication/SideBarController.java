package dictionaryapplication.dictionaryapplication;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

public class SideBarController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    private Parent homeParent;
    private Parent searchMainParent;

    private Parent bookMarkParent;
    private Parent helpParent;

    private HomeController homeController;
    private SearchController searchController;

    private BookMarkController bookMarkController;
    private HelpController helpController;

    public BorderPane getBorderPane() {
        return borderPane;
    }

    @FXML
    private BorderPane borderPane;


    @FXML
    private Label menu;


    @FXML
    private AnchorPane slider;

    @FXML
    private TextField autoTextField;

    @FXML
    public void home(ActionEvent event) throws IOException {
        loadPage(homeParent);
        //borderPane.setCenter(anchorPane);
    }

    @FXML
    public void search(ActionEvent event) throws IOException {
        loadPage(searchMainParent);
    }

    @FXML
    public void bookmark(ActionEvent event) throws IOException {
        loadPage(bookMarkParent);
    }

    @FXML
    public void help(ActionEvent event) throws IOException {
        loadPage(helpParent);
    }

    public void loadPage(Parent root) throws IOException {
        borderPane.setCenter(root);
    }

    /** Side Bar Transiting. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Auto text in search bar
        TextFields.bindAutoCompletion(autoTextField,
                "hey", "hello", "Hello World", "hoo", "hii", "haa", "huu").setVisibleRowCount(5);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            homeParent = loader.load();
            homeController = loader.getController();
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


    }
}
