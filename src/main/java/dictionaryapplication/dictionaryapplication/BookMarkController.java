package dictionaryapplication.dictionaryapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookMarkController extends CommonController implements Initializable {

    private Parent addWordParent;
    private AddWordController addWordController;
    private SideBarController sideBarController;

    @FXML
    private Button addNewButton;

    @FXML
    private Button removeButton;

    @FXML
    private ImageView searchImageView;

    @FXML
    private TextField searchInBankTextField;

    @FXML
    private TableView<?> wordBankTableView;

    public SideBarController getSideBarController() {
        return sideBarController;
    }

    public void init(SideBarController sideBarController) {
        this.sideBarController = sideBarController;
    }

    @Override
    public void loadPage(Parent parent) throws IOException {
        sideBarController.getBorderPane().setCenter(parent);
    }

    @FXML
    public void changeToAddWord(MouseEvent event) throws IOException {
        loadPage(addWordParent);
    }

    @FXML
    public void removeWord(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddWord.fxml"));
            addWordParent = loader.load();
            addWordController = loader.getController();
            addWordController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addNewButton.setCursor(Cursor.HAND);
        removeButton.setCursor(Cursor.HAND);
        searchImageView.setCursor(Cursor.HAND);
        searchInBankTextField.setCursor(Cursor.TEXT);


    }
}
