package model;

import controller.*;
import help.Help;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private static ScreenManager instance;
    private SideBarController sideBarController;
    private Parent sideBarParent;
    private Map<String, Parent> screens = new HashMap<>();
    private Map<String, Controller> controllers = new HashMap<>();
    private Stage primaryStage;

    private ScreenManager() {
        // Khởi tạo các controller và loaders ở đây
        try {
            String path = "/controller/" + "SideBar.fxml";
            URL url = getClass().getResource(path);

            if (url == null) {
                throw new FileNotFoundException("FXML file not found: " + path);
            }
            FXMLLoader loader = new FXMLLoader(url);
            sideBarParent = loader.load();
            sideBarController = loader.getController();
            controllers.put("SideBar", sideBarController);
        } catch (Exception e) {
            System.err.println("loi");
        }

        SearchController searchController = loadScreen("Search", "Search.fxml").getController();
        controllers.put("Search", searchController);

        HomeController homeController =  loadScreen("Home", "Home.fxml").getController();
        controllers.put("Home", homeController);

        BookMarkController bookMarkController = loadScreen("BookMark", "BookMark.fxml").getController();
        controllers.put("BookMark", bookMarkController);

        SearchTextController searchTextController = loadScreen("SearchText", "SearchText.fxml").getController();
        controllers.put("SearchText", searchTextController);

        SearchWordController searchWordController = loadScreen("SearchWord", "SearchWord.fxml").getController();
        controllers.put("SearchWord", searchWordController);

        FlashCardController flashCardController = loadScreen("FlashCard", "FlashCard.fxml").getController();
        controllers.put("FlashCard", flashCardController);

        HelpController helpController = loadScreen("Help", "Help.fxml").getController();
        controllers.put("Help", helpController);

        ShowWordController showWordController = loadScreen("ShowWord", "ShowWord.fxml").getController();
        controllers.put("ShowWord", showWordController);

        LogInController logInController =  loadScreen("LogIn", "LogIn.fxml").getController();
        controllers.put("LogIn", logInController);

        SignUpController signUpController =  loadScreen("SignUp", "SignUp.fxml").getController();
        controllers.put("SignUp", signUpController);

        CrosswordGameController crosswordGameController = loadScreen("CrosswordGame", "CrosswordGame.fxml").getController();
        controllers.put("CrosswordGame", crosswordGameController);

        HangmanGameController hangmanGameController = loadScreen("HangmanGame", "HangmanGame.fxml").getController();
        controllers.put("HangmanGame", hangmanGameController);

        AddWordController addWordController = loadScreen("AddWord", "AddWord.fxml").getController();
        controllers.put("AddWord", addWordController);
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void init(Stage primaryStage) {
        for (Controller controller : controllers.values()) {
            controller.init();
        }
        this.primaryStage = primaryStage;
        Scene scene = new Scene(sideBarParent);
        primaryStage.setScene(scene);
        try {
            setScreen("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Loggout");
            alert.setHeaderText("you're about to log out");
            alert.setContentText("do you want to save before logging out");
            if (alert.showAndWait().get() == ButtonType.OK) {
                Help.threadProcess(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        BookMarkManager.getInstance().save();
                        HistoryManager.getInstance().save();
                        BookMarkManager.getInstance().saveDatabase();
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        primaryStage.close();
                        System.out.println("log out successfully");
                    }
                }, sideBarController.getMainAnchorPane(), "SAVING...");
            }
        });
    }

    public FXMLLoader loadScreen(String name, String resource) {
        try {
            String path = "/controller/" + resource;  // Đảm bảo rằng thư mục chứa FXML có thể được tìm thấy trong classpath
            URL url = getClass().getResource(path);

            if (url == null) {
                throw new FileNotFoundException("FXML file not found: " + path);
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent screen = loader.load();
            screens.put(name, screen);
            return loader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setScreen(String name) throws IOException {
        sideBarController.setContent(screens.get(name));
    }

    public Controller getController(String name) {
        return controllers.get(name);
    }

    // Thêm các phương thức khác theo nhu cầu
}

