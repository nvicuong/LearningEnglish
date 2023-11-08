package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LogInController extends Controller implements Initializable {

    @FXML
    private Label createAccountLabel;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passWordFied;

    @FXML
    private TextField userNameTextField;

    private Parent signUpParent;
    private SignUpController signUpController;


    private HomeController homeController;

    private AutoCompletionBinding<String> autoCompletionBinding;

    private Set<String> possibleSuggestions = new HashSet<>();

    public HomeController getHomeController() {
        return homeController;
    }

    private void autoCompletionLearnWord(String newWord) {
        possibleSuggestions.add(newWord);

        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(userNameTextField, possibleSuggestions);
    }

    public void init(HomeController homeController) {
        this.homeController = homeController;
    }

    public void changeToSignUp(MouseEvent event) throws IOException {
        loadPage(signUpParent);
    }

    @Override
    public void loadPage(Parent parent) throws IOException {
        homeController.getSideBarController().loadPage(parent);
    }

    @FXML
    void logIn(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        autoCompletionBinding = TextFields.bindAutoCompletion(userNameTextField, possibleSuggestions);
        userNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                    autoCompletionLearnWord(userNameTextField.getText().trim());
                }
            }
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            signUpParent = loader.load();
            signUpController = loader.getController();
            signUpController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        createAccountLabel.setOnMouseClicked(mouseEvent -> {
            try {
                changeToSignUp(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        createAccountLabel.setCursor(Cursor.HAND);
        loginButton.setCursor(Cursor.HAND);
        passWordFied.setCursor(Cursor.TEXT);
        userNameTextField.setCursor(Cursor.TEXT);
    }
}
