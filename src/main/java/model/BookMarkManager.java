package model;

import controller.LogInController;
import database.UserDB;

import java.io.*;
import java.util.Scanner;

public class BookMarkManager extends Manager {

    private static BookMarkManager bookMarkManager;
    private final String BOOKMARK_PATH = "src/main/resources/data/wordBank.dat";

    private final String PATH = "src/main/resources/data/export.dat";

    private final String PATH1 = "src/main/resources/data/import.dat";

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

    public static BookMarkManager getBookMarkManager() throws IOException {
        if (bookMarkManager == null) {
            bookMarkManager = new BookMarkManager();
        }
        return bookMarkManager;
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


    public void exportFile() throws IOException {
        File file = new File(PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        for (Word word : wordList) {
            fileWriter.write(word.getSpelling() + "\n");
            //fileWriter.write("\n");
            fileWriter.write(word.getContent() + "\n");
        }
        fileWriter.close();
    }


    public void importFile() throws IOException {
        File file = new File(PATH1);
        if (!file.exists()) {
            file.createNewFile();
        }
        Scanner scanner = new Scanner(file);
        String s = "";
        while (scanner.hasNextLine()) {
            s += scanner.nextLine() + "\n";
        }
        String[] strings = s.split("\n");
        for (int i = 0; i < strings.length; i += 2) {
            Word word = new Word(strings[i], " ", strings[i + 1], " ");
            wordList.add(word);
        }
        scanner.close();
    }

    public void saveDatabase() {
        UserDB.save(wordList);
    }
}
