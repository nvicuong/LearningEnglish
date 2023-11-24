package database;

import model.HistoryManager;
import model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class ExecuteSQLFile {

    public static Connection connection;

    public static void init() throws SQLException {
        connection = JDBCUtil.getConnection();
    }

    public static ArrayList<String> searchWordListSpelling(String spelling) throws SQLException {
        Set<String> wordList = new LinkedHashSet<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM word WHERE spelling LIKE ? ORDER BY LENGTH(spelling) ASC, INSTR(spelling, ' ') DESC LIMIT 20")) {

            preparedStatement.setString(1, spelling + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    wordList.add(resultSet.getString("spelling"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println(wordList);
        return new ArrayList<>(wordList);
    }

    public static Word searchWord(String s) throws SQLException {
        Word word = new Word();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM word WHERE spelling = ?")) {

            preparedStatement.setString(1, s);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Xử lý kết quả

                    String spelling = resultSet.getString("spelling");
                    String pronunciation = resultSet.getString("pronunciation");
                    String content = resultSet.getString("content");
                    String synonym = resultSet.getString("synonym");

                    word.setSpelling(spelling);
                    word.setPronunciation(pronunciation);
                    word.setContent(content);
                    word.setSynonym(synonym);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return word;
    }

    public static Word getRandomWord() throws SQLException {
        String randomWord = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT spelling FROM word ORDER BY RANDOM() LIMIT 1")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    randomWord = resultSet.getString("spelling");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchWord(randomWord);
    }
}
