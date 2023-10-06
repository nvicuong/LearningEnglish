package dictionaryapplication.dictionaryapplication;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.*;

public class LogInController extends CommonController implements Initializable {

    @FXML
    private TextField learnTextField;

    private AutoCompletionBinding<String> autoCompletionBinding;

    private Set<String> possibleSuggestions = new HashSet<>();


    private void autoCompletionLearnWord(String newWord) {
        possibleSuggestions.add(newWord);

        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(learnTextField, possibleSuggestions);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        autoCompletionBinding = TextFields.bindAutoCompletion(learnTextField, possibleSuggestions);
        learnTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                    autoCompletionLearnWord(learnTextField.getText().trim());
                }
            }
        });
    }
}
