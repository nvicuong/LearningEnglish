package help;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    public static void loggout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Loggout");
        alert.setHeaderText("you're about to log out");
        alert.setContentText("do you want to save before logging out");
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("log out successfully");
            stage.close();
        }
    }
}
