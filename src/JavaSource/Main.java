package JavaSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("simpleHangman.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Game To Children");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
    }
}