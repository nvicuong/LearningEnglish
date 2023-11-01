package controller;

import database.ExecuteSQLFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class DictionaryApplication extends Application {
    SideBarController sideBarController;
    Parent sideBarParent;

    private void runCppFile() throws IOException, InterruptedException {
        Process compileProcess = new ProcessBuilder("g++",
                "src/main/java/games/gener.cpp", "-o", "gener").start();
        compileProcess.waitFor();

        int SIZE = 12, SHRT = 8, LONG = 4;

        Process runProcess = new ProcessBuilder("./gener",
                String.valueOf(SIZE), String.valueOf(SHRT), String.valueOf(LONG)).start();
        InputStream inputStream = runProcess.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        for (int i = 0; i < SIZE + SHRT + LONG; i++) {
            String line = bufferedReader.readLine();
            System.out.print("C++ Program Output: ");
            for (char c : line.toCharArray()) {
                System.out.print(c);
                if (i >= SHRT + LONG) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        runProcess.waitFor();
    }

    @Override
    public void start(Stage stage) throws Exception {
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

        runCppFile();
    }

    public static void main(String[] args) {
        launch();
    }
}