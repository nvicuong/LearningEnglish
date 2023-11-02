package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.BookMarkManager;
import model.Word;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookMarkController extends CommonController implements Initializable {

    public BookMarkManager getBookMarkManager() {
        return bookMarkManager;
    }
    private BookMarkManager bookMarkManager;
    private Parent addWordParent;
    private Parent hangmanGameParent;
    private HangmanGameController hangmanGameController;
    private Parent flashCardParent;
    private FlashCardController flashCardController;
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
    private TableView<Word> wordBankTableView;

    @FXML
    private TableColumn<Word, String> contentCollumn;

    @FXML
    private TableColumn<Word, String> pronunciationCollumn;

    @FXML
    private TableColumn<Word, String> spellingCollumn;

    @FXML
    private TableColumn<Word, String> synonymCollumn;

    @FXML
    private Button viewWordButton;

    @FXML
    private Button flashCardButton;

    @FXML
    private Button hangmanGameButton;

    @FXML
    private Button removeAllButton;

    private ObservableList<Word> wordBankList;

    public SideBarController getSideBarController() {
        return sideBarController;
    }

    public void init(SideBarController sideBarController) {
        this.sideBarController = sideBarController;
    }

    public void updateWord() {
        wordBankList.clear();
        wordBankList.addAll(bookMarkManager.getWordBank());
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
    void changeToCrosswordGame(MouseEvent event) throws IOException {
        loadPage(crosswordParent);
        if (crosswordGameController.getMatrixFlowPane().getChildren().isEmpty()) {
            Task<Void> task = RunCrosswordGame.getRunCrosswordGame().createTask(12, 7, 5);
            new Thread(task).start();

            task.setOnSucceeded(e -> {
                crosswordGameController.loadMatrix();
            });
        }

    }

    @FXML
    void changeToFlashCard(MouseEvent event) throws IOException {
        loadPage(flashCardParent);
        flashCardController.start(bookMarkManager.getWordBank());
    }

    @FXML
    void changeToHangmanGame(MouseEvent event) throws IOException {
        if (bookMarkManager.getWordBank().isEmpty()) {
            showNotification("Word Bank is empty", "save more word to play game");
            return;
        }
        loadPage(hangmanGameParent);
        hangmanGameController.start(bookMarkManager.getWordBank());
    }

    @FXML
    void getItem(MouseEvent event) throws IOException {

    }

    @FXML
    void viewWord(MouseEvent event) throws IOException {
        int index = wordBankTableView.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        }

        Word word = new Word(spellingCollumn.getCellData(index), pronunciationCollumn.getCellData(index), contentCollumn.getCellData(index), synonymCollumn.getCellData(index));
        sideBarController.changeToShowWord(word);
    }

    @FXML
    void removeAllWord(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING");
        alert.setHeaderText("Remove all");
        alert.setContentText("Are you sure to delete all?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            bookMarkManager.getWordBank().clear();
            updateWord();
            bookMarkManager.updateWordBankSpelling();
            sideBarController.getHomeController().updateBookmarkList();
        }
    }

    @FXML
    public void removeWord(MouseEvent event) {
        int index = wordBankTableView.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        }

        Word word = new Word(spellingCollumn.getCellData(index), pronunciationCollumn.getCellData(index), contentCollumn.getCellData(index), synonymCollumn.getCellData(index));
        bookMarkManager.getWordBank().removeIf(word1 -> {
            return word1.equals(word);
        });
        updateWord();
        bookMarkManager.updateWordBankSpelling();
        sideBarController.getHomeController().updateBookmarkList();
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


        try {
            bookMarkManager = new BookMarkManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FlashCard.fxml"));
            flashCardParent = loader.load();
            flashCardController = loader.getController();
            flashCardController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("HangmanGame.fxml"));
        try {
            hangmanGameParent = loader.load();
            hangmanGameController = loader.getController();
            hangmanGameController.init(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //khởi tạo bảng
        wordBankList = FXCollections.observableArrayList(bookMarkManager.getWordBank());
        spellingCollumn.setCellValueFactory(new PropertyValueFactory<Word, String>("spelling"));
        pronunciationCollumn.setCellValueFactory(new PropertyValueFactory<Word, String>("pronunciation"));
        contentCollumn.setCellValueFactory(new PropertyValueFactory<Word, String>("content"));
        synonymCollumn.setCellValueFactory(new PropertyValueFactory<Word, String>("synonym"));
        wordBankTableView.setItems(wordBankList);

        //lọc bảng
        FilteredList<Word> filteredData = new FilteredList<>(wordBankList, b -> true);

        searchInBankTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(word -> {

                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return word.getSpelling().toLowerCase().contains(searchKeyword);
            });
        });

        SortedList<Word> sortedList = new SortedList<>(filteredData);

        sortedList.comparatorProperty().bind(wordBankTableView.comparatorProperty());

        wordBankTableView.setItems(sortedList);

        //giới hạn hiển thị nội dung trong bảng
        contentCollumn.setCellFactory(column -> new TableCell<Word, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.substring(0, Math.min(item.length(), 50)));
                }
            }
        });

        //click 2 phát thì chuyển sang searh luôn
        wordBankTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Word word = wordBankTableView.getSelectionModel().getSelectedItem();
                if (word != null) {
                    try {
                        sideBarController.changeToShowWord(word);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        addNewButton.setCursor(Cursor.HAND);
        removeButton.setCursor(Cursor.HAND);
        searchImageView.setCursor(Cursor.HAND);
        hangmanGameButton.setCursor(Cursor.HAND);
        flashCardButton.setCursor(Cursor.HAND);
        viewWordButton.setCursor(Cursor.HAND);
        removeAllButton.setCursor(Cursor.HAND);
        searchInBankTextField.setCursor(Cursor.TEXT);


    }
}
