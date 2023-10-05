package dictionaryapplication.dictionaryapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SearchController implements Initializable {

    @FXML
    private TextField autoTextField;

    @FXML
    private TextField learnTextField;

    private SideBarController sideBarController;

    private Parent searchWordParent;
    private Parent searchTextParent;

    private SearchTextController searchTextController;
    private SearchWordController searchWordController;

    private AutoCompletionBinding<String> autoCompletionBinding;
    private String[] _possibleSuggestion = {"Hey", "Hello", "Hello World"};

    private Set<String> possibleSuggestions = new HashSet<>(Arrays.asList(_possibleSuggestion));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TextFields.bindAutoCompletion(autoTextField,
                "hey", "hello", "Hello World", "hoo", "hii", "haa", "huu").setVisibleRowCount(5);

        autoCompletionBinding = TextFields.bindAutoCompletion(learnTextField, possibleSuggestions);
        learnTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                    autoCompletionLearnWord(learnTextField.getText().trim());
                }
            }
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchWord.fxml"));
            searchWordParent = loader.load();
            searchWordController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchText.fxml"));
            searchTextParent = loader.load();
            searchTextController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void autoCompletionLearnWord(String newWord) {
        possibleSuggestions.add(newWord);

        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(learnTextField, possibleSuggestions);
    }

    public void changeToSearchWord(ActionEvent event) {
        sideBarController.getBorderPane().setCenter(searchWordParent);
    }

    public void changeToTranslateText(ActionEvent event) {
        sideBarController.getBorderPane().setCenter(searchTextParent);
    }

    public void init(SideBarController sideBarController) {
        this.sideBarController = sideBarController;
    }

}
