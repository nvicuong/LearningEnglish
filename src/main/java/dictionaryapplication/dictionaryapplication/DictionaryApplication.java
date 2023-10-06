package dictionaryapplication.dictionaryapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("SideBar.fxml"));
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/dictionaryapplication/image/windowIcon.png")).toExternalForm());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MY DICK");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            CommonController.loggout(stage);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}