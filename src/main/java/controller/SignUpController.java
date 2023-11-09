package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController extends Controller implements Initializable {

    private LogInController logInController;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label logInButton;

    @FXML
    private PasswordField passWordFied;

    @FXML
    private PasswordField reTypePasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField userNameTextField;

    @FXML
    void signUp(MouseEvent event) {

    }

    public void init(LogInController logInController) {
        this.logInController = logInController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInButton.setOnMouseClicked(mouseEvent -> {
            try {
                logInController.getHomeController().changeToLogin(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        emailTextField.setCursor(Cursor.TEXT);
        passWordFied.setCursor(Cursor.TEXT);
        reTypePasswordField.setCursor(Cursor.TEXT);
        userNameTextField.setCursor(Cursor.TEXT);
        signUpButton.setCursor(Cursor.HAND);
        logInButton.setCursor(Cursor.HAND);
    }

}
