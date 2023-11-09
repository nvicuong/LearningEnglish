package model;

import java.io.*;
import java.util.*;

public class BookMarkManager extends Manager {

    private static BookMarkManager bookMarkManager;
    private final String BOOKMARK_PATH = "src/main/resources/data/wordBank.dat";

    public static BookMarkManager getBookMarkManager() throws IOException {
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

    public void removeWord(Word word) throws IOException {
        BookMarkManager.getBookMarkManager().getWordList().removeIf(word1 -> {
            return word1.equals(word);
        });
    }
}
