package controller;

import com.google.cloud.firestore.FirestoreException;
import database.UserDB;
import help.Help;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.BookMarkManager;
import model.ScreenManager;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

public class LogInController extends Controller implements Initializable {

    @FXML
    private Label createAccountLabel;

    @FXML
    private AnchorPane homeAnchorPane;

    @FXML
    private Button loginButton;

    @FXML
    private Text errorLog;

    @FXML
    private PasswordField passWordFied;

    @FXML
    private TextField userNameTextField;

    private HomeController homeController;
    private BookMarkController bookMarkController;

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


    public void changeToSignUp(MouseEvent event) throws IOException {
        ScreenManager.getInstance().setScreen("SignUp");
    }

    @Override
    public void init() {
        homeController = (HomeController) ScreenManager.getInstance().getController("Home");
        bookMarkController = (BookMarkController) ScreenManager.getInstance().getController("BookMark");
    }

    @FXML
    void logIn(MouseEvent event) throws Exception {
        Help.threadProcess(createTask(), homeAnchorPane, "Log in...");
    }

    public Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Thực hiện các tác vụ đồng bộ tại đây
                String username = userNameTextField.getText();
                String password = passWordFied.getText();
                try {
                    UserDB.Credential.login(username, password);
                    BookMarkManager.getInstance().fetch();
                } catch (Exception e) {
                    errorLog.setText(e.getMessage());
                    errorLog.setVisible(true);
                    errorLog.setStyle("-fx-fill: red");
                    return null;
                }

                userNameTextField.clear();
                passWordFied.clear();
                errorLog.setVisible(false);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                if (!errorLog.isVisible()) {
                    try {
                        homeController.updateBookmarkList();
                        bookMarkController.updateWord();
                        ScreenManager.getInstance().setScreen("Home");
                        homeController.resetUser();
                        Help.showNotification("Notification", "Log in successfully!");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    public void signInSuccess() {
        userNameTextField.clear();

        passWordFied.clear();

        // ask user to login
        errorLog.setVisible(true);
        errorLog.setText("Sign up successfully! Please login");

        // set errorLog color to green
        errorLog.setStyle("-fx-fill: green");
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

//        signUpController = (SignUpController) ScreenManager.getInstance().getController("SignUp");
//        signUpController.init(this);

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

        errorLog.setVisible(false);
    }

    public static void saveLastLogin(String account) throws FirestoreException {
        String fileName = "src/main/resources/data/lastLogin.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(account);

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static String readLastLogin() {
        String fileName = "src/main/resources/data/lastLogin.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            line = reader.readLine();
            if (line == null) {
                return " ";
            }
            return line;
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return " ";
    }
}
