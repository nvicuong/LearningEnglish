package controller;

import database.UserDB;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.*;


public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        UserDB.initialize();
        TranslateAPI.initialize();
        WordManager.getWordManager();
        HistoryManager.getInstance();
        BookMarkManager.getInstance();

        Image icon = new Image(getClass().getResource("/image/windowIcon.png").toExternalForm());
        stage.setTitle("DICTIONARY");
        stage.getIcons().add(icon);
        ScreenManager.getInstance().init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}