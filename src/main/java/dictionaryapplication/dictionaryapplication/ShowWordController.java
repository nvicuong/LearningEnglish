package dictionaryapplication.dictionaryapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Word;

public class ShowWordController extends CommonController {
    @FXML
    private TextArea contentTextArea;

    @FXML
    private Label pronunciationLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Label spellingLabel;

    @FXML
    private Label synonymLabel;

    public void setContent(Word word) {
        spellingLabel.setText(word.getSpelling());
        pronunciationLabel.setText(word.getPronunciation().replaceAll("\\^", "'"));
        synonymLabel.setText(word.getSynonym());
        String[] arr = word.getContent().split("\\|");
        String a = "";
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
                a += sub;
                a += ".";
                a += "\n";
            }
        }
//        System.out.println(a);
        contentTextArea.setText(a);
    }
}
