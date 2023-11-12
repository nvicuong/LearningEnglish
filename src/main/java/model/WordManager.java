package model;

import database.ExecuteSQLFile;
import help.Help;
import javafx.concurrent.Task;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class WordManager {

    private static WordManager wordManager;

    public static WordManager getWordManager() throws IOException, ClassNotFoundException, SQLException {
        if (wordManager == null) {
            wordManager = new WordManager();
        }
        return wordManager;
    }

    private WordManager() throws SQLException {
        ExecuteSQLFile.init();
    }

    public Word getRandomWord() throws SQLException {
        return ExecuteSQLFile.getRandomWord();
    }

    public List<String> searchWordList(String s) throws SQLException {
        return new ArrayList<>(ExecuteSQLFile.searchWordListSpelling(s));
    }

    public Word searchWord(String s) throws SQLException {
        return ExecuteSQLFile.searchWord(s);
    }
}
