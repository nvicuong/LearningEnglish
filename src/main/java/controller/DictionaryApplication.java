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
import model.ScreenManager;
import model.WordManager;

import java.io.IOException;


public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        UserDB.initialize();
        WordManager.getWordManager();
        HistoryManager.getHistoryManager();
        BookMarkManager.getBookMarkManager();

        Image icon = new Image(getClass().getResource("/image/windowIcon.png").toExternalForm());
        stage.setTitle("DICTIONARY");
        stage.getIcons().add(icon);
        ScreenManager.getInstance().init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}