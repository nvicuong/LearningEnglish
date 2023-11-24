package help;

import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Word;
import org.controlsfx.control.Notifications;

public class Help {
    public static Word formatWord(Word word) {
        String spelling = word.getSpelling().replaceAll("\\^", "'");
        String pronunciation = word.getPronunciation().replaceAll("\\^", "'");
        String synonym = word.getSynonym().replaceAll("\\^", "'");
        String[] arr = word.getContent().replaceAll("\\^", "'").split("\\|");
        String content = "";
        for (String s : arr) {
            String[] defi = s.split("\\\\n");
            for (String i : defi) {
                if (i.equals(", ") || i.isBlank()) {
                    continue;
                }
                String sub = i;
                if (i.indexOf(", ") == 0 && i.length() > 2) {
                    sub = i.substring(1, i.length() - 1);
                }
                if (sub.lastIndexOf(", ") == sub.length() - 2 && sub.length() >= 2) {
                    sub = sub.substring(0, sub.length() - 2);
                }
                content += sub;
                content += "\n";
            }
        }
        return new Word(spelling, pronunciation, content, synonym);
    }

    public static Word unformatWord(Word word) {
        String spelling = word.getSpelling();
        String pronunciation = word.getPronunciation();
        String synonym = word.getSynonym();
        String content = word.getContent().replaceAll("<", "|<").replaceAll("\n", "\\\\n");
        return new Word(spelling, pronunciation, content, synonym);
    }

    public static void showNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showInformation();
    }

    public static void showAlertWarning(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
    }

    public static void threadProcess(Task<Void> task, AnchorPane anchorPane, String massage) {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        Label label = new Label(massage);
        ProgressIndicator loadingProgressIndicator = new ProgressIndicator();
        loadingProgressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        loadingProgressIndicator.progressProperty().bind(task.progressProperty());
        loadingProgressIndicator.setLayoutX((anchorPane.getWidth() - loadingProgressIndicator.getWidth()) / 2);
        loadingProgressIndicator.setLayoutY((anchorPane.getHeight() - loadingProgressIndicator.getHeight()) / 2 - 50);
        label.setLayoutX(loadingProgressIndicator.getLayoutX());
        label.setLayoutY(loadingProgressIndicator.getLayoutY() + 50);
        anchorPane.getChildren().addAll(loadingProgressIndicator, label);
        task.setOnRunning(e -> {
            loadingProgressIndicator.setVisible(true);
            label.setVisible(true);
        });
        task.setOnSucceeded(e -> {
            loadingProgressIndicator.setVisible(false);
            label.setVisible(false);
            anchorPane.getChildren().remove(loadingProgressIndicator);
            anchorPane.getChildren().remove(label);
        });
    }
}
