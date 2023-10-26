package model;

import controller.ShowWordController;
import help.Help;
import javafx.scene.Parent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookMarkManager {


    private List<Word> wordBank;
    private final String BOOKMARK_PATH = "src\\\\main\\\\resources\\\\data\\\\wordBank.txt";

    public BookMarkManager() {
        wordBank = readWordBank();
    }

    public List<Word> getWordBank() {
        return wordBank;
    }



    public void saveWordBank() throws IOException {
        File file = new File(BOOKMARK_PATH);
        FileWriter writer = new FileWriter(file, false);
        for (Word word : wordBank) {
            writer.write(word.toString());
            writer.write('\n');
        }
        writer.close();
    }

    public void addWordToBank(Word word) throws IOException {
        wordBank.removeIf(word1 -> {
            return word1.equals(word);
        });
        wordBank.add(0, word);
    }

    public List<Word> readWordBank() {
        InputStream inputStream = HistoryManager.class.getResourceAsStream("/data/wordBank.txt");
        assert inputStream != null;
        Scanner scanner = new Scanner(inputStream);
        List<Word> words = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String[] arr = s.split("\\? ");
            if (arr.length == 4) {
                words.add(new Word(arr[0], arr[1], arr[2], arr[3]));
            }
            else {
                words.add(new Word(arr[0], arr[1], arr[2], ""));
            }
        }
        return words;
    }
}