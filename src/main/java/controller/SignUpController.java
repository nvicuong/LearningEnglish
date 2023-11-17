package controller;

import database.UserDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
    private Text errorLog;

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
        String username = userNameTextField.getText();
        String password = passWordFied.getText();
        String retypePassword = reTypePasswordField.getText();

        errorLog.setVisible(true);

        if (username.isEmpty()) {
            errorLog.setText("Username is empty");
            return;
        }

        if (password.isEmpty()) {
            errorLog.setText("Password is empty");
            return;
        }

        if (retypePassword.isEmpty()) {
            errorLog.setText("Retype password is empty");
            return;
        }

        if (!password.equals(retypePassword)) {
            errorLog.setText("Password and retype password are not the same");
            return;
        }

        try {
            UserDB.signin(username, password);
        } catch (Exception e) {
            errorLog.setText(e.getMessage());
            return;
        }

        errorLog.setVisible(false);

        try {
            logInController.signInSuccess();
            logInController.getHomeController().changeToLogin(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        errorLog.setVisible(false);
    }

}
