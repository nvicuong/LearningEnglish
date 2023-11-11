package model;

import database.ExecuteSQLFile;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class WordManager {
    
    public Word getRandomWord() throws SQLException {
        return ExecuteSQLFile.getRandomWord();
    }

    public List<String> searchWordList(String s) throws SQLException {
        return new ArrayList<>(ExecuteSQLFile.searchWordListSpelling(s));
    }

}
