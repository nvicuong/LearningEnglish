package controller;

import database.UserDB;
import help.Help;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.BookMarkManager;
import model.HistoryManager;
import model.WordManager;

import java.io.IOException;


public class DictionaryApplication extends Application {
    SideBarController sideBarController;
    Parent sideBarParent;

    @Override
    public void start(Stage stage) throws Exception {
        UserDB.initialize();

        WordManager.getWordManager();
        HistoryManager.getHistoryManager();
        BookMarkManager.getBookMarkManager();

        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("SideBar.fxml"));
        sideBarParent = fxmlLoader.load();
        sideBarController = fxmlLoader.getController();
        Image icon = new Image(getClass().getResource("/image/windowIcon.png").toExternalForm());

        Scene scene = new Scene(sideBarParent);
        stage.setTitle("DICTIONARY");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Loggout");
            alert.setHeaderText("you're about to log out");
            alert.setContentText("do you want to save before logging out");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Help.threadProcess(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        BookMarkManager.getBookMarkManager().save();
                        HistoryManager.getHistoryManager().save();
                        BookMarkManager.getBookMarkManager().saveDatabase();
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        stage.close();
                        System.out.println("log out successfully");
                    }
                }, sideBarController.getMainAnchorPane(), "SAVING...");
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }
}