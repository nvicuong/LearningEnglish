package model;

import controller.LogInController;
import database.UserDB;

import java.io.*;

public class BookMarkManager extends Manager {

    private static BookMarkManager bookMarkManager;
    private final String BOOKMARK_PATH = "src/main/resources/data/wordBank.dat";

    public static BookMarkManager getInstance() throws IOException {
        if (bookMarkManager == null) {
            bookMarkManager = new BookMarkManager();
        }
        return bookMarkManager;
    }

    private BookMarkManager() throws IOException {
        super();
        read(BOOKMARK_PATH);
        updateWordSpelling();
    }

    @Override
    public void save() throws IOException {
        super.save(BOOKMARK_PATH);
    }

    public void fetch() throws Exception {
        String s = LogInController.readLastLogin();
        if (s.equals(UserDB.getUsername())) {
            UserDB.addWordAll(wordList);
        }
        wordList.clear();
        wordList.addAll(UserDB.getAllWords());
        UserDB.clearAll();
        LogInController.saveLastLogin(UserDB.getUsername());
    }

    public void removeWord(Word word) throws IOException {
        BookMarkManager.getInstance().getWordList().removeIf(word1 -> {
            return word1.equals(word);
        });
    }

    public void saveDatabase() {
        UserDB.save(wordList);
    }
}
