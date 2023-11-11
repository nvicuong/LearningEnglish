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

    public static void selectWord(String s) {
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

                    System.out.println("Spelling: " + spelling);
                    System.out.println("Pronunciation: " + pronunciation);
                    System.out.println("Content: " + content);
                    System.out.println("Synonym: " + synonym);
                    System.out.println("------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void readAll() {
        String countQuery = "SELECT COUNT(*) AS rowCount FROM word";

        // Thực hiện truy vấn và xử lý kết quả
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(countQuery)) {

            if (resultSet.next()) {
                int rowCount = resultSet.getInt("rowCount");
                System.out.println("Số lượng dữ liệu: " + rowCount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() throws SQLException {
        JDBCUtil.closeConnection(connection);
    }

    public static void insertWord(Word word) throws SQLException {
        Statement statement = connection.createStatement();
        String insertData = "INSERT INTO word (spelling, pronunciation, content, synonym) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertData)) {
            // Thiết lập giá trị cho các tham số
            preparedStatement.setString(1, word.getSpelling());
            preparedStatement.setString(2, word.getPronunciation());
            preparedStatement.setString(3, word.getContent());
            preparedStatement.setString(4, word.getSynonym());

            // Thực thi câu lệnh INSERT
            preparedStatement.executeUpdate();
            System.out.println(word.getSpelling());
        }
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
