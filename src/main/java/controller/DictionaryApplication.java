package controller;

import database.ExecuteSQLFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import atlantafx.base.theme.*;

import java.io.IOException;


public class DictionaryApplication extends Application {
    SideBarController sideBarController;
    Parent sideBarParent;
    @Override
    public void start(Stage stage) throws Exception {
        ExecuteSQLFile.init();
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("SideBar.fxml"));
        sideBarParent = fxmlLoader.load();
        sideBarController = fxmlLoader.getController();
        Image icon = new Image(getClass().getResource("/image/windowIcon.png").toExternalForm());

        Scene scene = new Scene(sideBarParent);
        stage.setTitle("MY DICK");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            try {
                sideBarController.getHistoryManager().saveWordToHistory();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                sideBarController.getBookMarkController().getBookMarkManager().saveWordBank();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            CommonController.loggout(stage);
        });

    }

    public static void main(String[] args) {
        launch();
    }
}