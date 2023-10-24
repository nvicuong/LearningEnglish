package dictionaryapplication.dictionaryapplication;

import database.ExecuteSQLFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        SourceReader.start();
        ExecuteSQLFile.init();
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
            try {
                ExecuteSQLFile.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }
}