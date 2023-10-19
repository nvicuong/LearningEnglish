package dictionaryapplication.dictionaryapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import dictionaryapplication.dictionaryapplication.data.*;

import static dictionaryapplication.dictionaryapplication.WordSearcher.search;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("SideBar.fxml"));
        Image icon = new Image(getClass().getResource("/dictionaryapplication/image/windowIcon.png").toExternalForm());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MY DICK");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            CommonController.loggout(stage);
        });

        SourceReader.start();
        System.out.println(Dictionary.getSize());

        WordSearcher.init();
        ArrayList<Integer> a = WordSearcher.search("chạy");
        for (int wordIdx : a) {
            Word word = Dictionary.getWord(wordIdx);
            System.out.println(word.toString());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}