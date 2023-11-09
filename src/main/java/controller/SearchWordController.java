package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.HistoryManager;
import model.Word;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchWordController extends Controller implements Initializable {


    private SearchController searchController;

    @FXML
    private ImageView back;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<Word> historyTableView;

    @FXML
    private TableColumn<Word, String> spellingCollumn;

    @FXML
    private TableColumn<Word, String> pronunciationCollumn;

    @FXML
    private TableColumn<Word, String> contentCollumn;

    @FXML
    private TableColumn<Word, String> synonymCollumn;

    private ObservableList<Word> historyWordList;


    @FXML
    private TextField searchWordTextField;

    @FXML
    void removeAllWord(MouseEvent event) throws IOException {
        HistoryManager.getHistoryManager().getWordList().clear();
        updateWord();
        searchController.getSideBarController().getHomeController().updateHistoryList();
    }

    public void updateWord() throws IOException {
        historyWordList.clear();
        historyWordList.addAll(HistoryManager.getHistoryManager().getWordList());
    }


    public void init(SearchController searchController) {
        this.searchController = searchController;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //khởi tạo bảng
        try {
            historyWordList = FXCollections.observableArrayList(HistoryManager.getHistoryManager().getWordList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        spellingCollumn.setCellValueFactory(new PropertyValueFactory<Word, String>("spelling"));
        pronunciationCollumn.setCellValueFactory(new PropertyValueFactory<Word, String>("pronunciation"));
        contentCollumn.setCellValueFactory(new PropertyValueFactory<Word, String>("content"));
        synonymCollumn.setCellValueFactory(new PropertyValueFactory<Word, String>("synonym"));
        historyTableView.setItems(historyWordList);

        //lọc từ trong bảng
        FilteredList<Word> filteredData = new FilteredList<>(historyWordList, b -> true);

        searchWordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(word -> {

                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return word.getSpelling().toLowerCase().contains(searchKeyword);
            });
        });

        SortedList<Word> sortedList = new SortedList<>(filteredData);

        sortedList.comparatorProperty().bind(historyTableView.comparatorProperty());

        historyTableView.setItems(sortedList);

        //click 2 phát thì chuyển sang searh luôn
        historyTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Word word = historyTableView.getSelectionModel().getSelectedItem();
                if (word != null) {
                    try {
                        searchController.getSideBarController().searchWord(word.getSpelling());
                    } catch (SQLException | IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        contentCollumn.setCellFactory(column -> new TableCell<Word, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.substring(0, Math.min(item.length(), 100)));
                }
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            try {
                searchController.getSideBarController().changeToMainSearch(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        back.setCursor(Cursor.HAND);
        refreshButton.setCursor(Cursor.HAND);
        searchWordTextField.setCursor(Cursor.TEXT);
    }

}
