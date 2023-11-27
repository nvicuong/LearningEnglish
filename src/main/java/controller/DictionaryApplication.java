package controller;

import database.UserDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/Help.fxml"));
//        Scene scene = new Scene(loader.load());
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}