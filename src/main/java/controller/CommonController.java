package controller;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class CommonController {

    public void loadPage(Parent root) throws IOException {}

    public void showNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showInformation();
    }

    public void showAlertWarning(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
    }

    public static void loggout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Loggout");
        alert.setHeaderText("you're about to log out");
        alert.setContentText("do you want to save before logging out");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("log out successfully");
            stage.close();
        }
    }
}
