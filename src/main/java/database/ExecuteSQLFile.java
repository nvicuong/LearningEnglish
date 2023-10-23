package database;

import dictionaryapplication.dictionaryapplication.SourceReader;
import model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ExecuteSQLFile {

    public static Connection connection;
    public static Statement statement;

    public static final String queqyListSpelling = "SELECT spelling\n" +
            "FROM word.word\n" +
            ";";

    public static final String queqyList = "SELECT spelling\n" +
            "FROM word.word\n" +
            "WHERE spelling REGEXP '^%s'\n" +
            ";";
    public static final String queqyExact = "SELECT c.spelling, w.pronunciation, c.pos, c.definition_pharse, w.synonym\n" +
            "FROM word.word AS w\n" +
            "JOIN word.content AS c ON w.spelling = c.spelling\n" +
            "WHERE w.spelling = '%s'\n" +
            ";";

    public static void initSQLDatabase() throws SQLException {
        restart();
        start("word");
        start("content");
    }

    public static void init() throws SQLException {
        connection = JDBCUtil.getConnection();
        statement = connection.createStatement();
    }

    public static void close() throws SQLException {
        JDBCUtil.closeConnection(connection);
    }

    private static String getFileContent(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (InputStream inputStream = ExecuteSQLFile.class.getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static void start(String index) throws SQLException {
        InputStream inputStream = SourceReader.class.getResourceAsStream("/data/" + index + ".txt");
        if (inputStream == null) {
            System.err.println("File not found.");
            return;
        }
        String insert = "INSERT INTO `word`.`word` \n" +
                "VALUES %s;";
        if (index.equals("word")) {
            insert = "INSERT INTO `word`.`word` \n" +
                    "VALUES %s;";
        } else if (index.equals("content")) {
            insert = "INSERT INTO `word`.`content` \n" +
                    "VALUES %s;";
        }
        Connection connection = JDBCUtil.getConnection();
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(inputStream);
        int count = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            line = line.replaceAll("\\),", ")");
            count++;
            System.out.println(count + line);
            int check = statement.executeUpdate(String.format(insert, line));
        }
        JDBCUtil.closeConnection(connection);
    }


    public static void restart() {
        try {
            String[] createQuery = {
                    "DROP DATABASE IF EXISTS `word`;",
                    "CREATE DATABASE `word`;",
                    "USE `word`;",
                    "DROP TABLE IF EXISTS `content`;\n",
                    "CREATE TABLE `content` (\n" +
                            "  `spelling` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,\n" +
                            "  `pos` varchar(45) DEFAULT NULL,\n" +
                            "  `definition_pharse` varchar(10000) DEFAULT NULL\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;",
                    "DROP TABLE IF EXISTS `word`;",
                    "CREATE TABLE `word` (\n" +
                            "  `spelling` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,\n" +
                            "  `pronunciation` varchar(100) DEFAULT NULL,\n" +
                            "  `synonym` varchar(500) DEFAULT NULL\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;",
                    "ALTER TABLE `content`\n" +
                            "  ADD KEY `fk_content_word` (`spelling`);",
                    "ALTER TABLE `word`\n" +
                            "  ADD PRIMARY KEY (`spelling`);",
                    "ALTER TABLE `content`\n" +
                            "  ADD CONSTRAINT `fk_content_word` FOREIGN KEY (`spelling`) REFERENCES `word` (`spelling`);\n"
            };

            int count = 0;
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();

            System.out.println(count);
            for (String query : createQuery) {
                if (query.trim().endsWith(";")) {
                    query = query.substring(0, query.length() - 1);
                }
                if (query.startsWith("ALTER TABLE")) {
                    statement.execute(query);
                } else {
                    System.out.println(query);
                    statement.executeUpdate(query + ";");
                }
            }

            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> searchWordListSpelling(String spelling) throws SQLException {
        String query = String.format(queqyList, spelling);
        ResultSet resultSet = statement.executeQuery(query);
        Set<String> wordList = new LinkedHashSet<>();
        int count = 0;
        while (resultSet.next()) {
            if (count > 20) {
                break;
            }
            wordList.add(resultSet.getString("spelling"));
            count++;
        }
        return new ArrayList<>(wordList);
    }

    public static Word searchWord(String spelling) throws SQLException {
        Connection connection = JDBCUtil.getConnection();
        Statement statement = connection.createStatement();
        String query = String.format(queqyExact, spelling);
        ResultSet resultSet = statement.executeQuery(query);
        Word wordDAO = new Word();
        while (resultSet.next()) {
            wordDAO.setSpelling(resultSet.getString("spelling"));
            wordDAO.setPronunciation(resultSet.getString("pronunciation"));
            wordDAO.setSynonym(resultSet.getString("synonym"));
        }
        connection.close();
        return wordDAO;
    }

    public static String getWord(Word wordDAO) {
        return wordDAO.getSpelling() +
                ", " +
                wordDAO.getPronunciation() +
                ", " +
                wordDAO.getSynonym() +
                ", " +
                wordDAO.getContent();
    }

    public static void getAllWord() throws SQLException {
        ResultSet resultSet = statement.executeQuery(queqyListSpelling);
        Set<String> wordList = new LinkedHashSet<>();
        while (resultSet.next()) {
            wordList.add(resultSet.getString("spelling"));
        }
        for (String s : wordList) {
            Word wordDAO = searchWord(s);
            String string = getWord(wordDAO);
            System.out.println(string);
        }
        System.out.println("thanh cong");
    }

}
