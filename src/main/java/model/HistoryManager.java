package model;

import help.Help;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class HistoryManager {

    private List<Word> historyWord;
    private final String HISTORYWORD_PATH = "src\\\\main\\\\resources\\\\data\\\\historyWord.txt";

    public HistoryManager() {
        historyWord = readHistory();
    }

    public List<Word> getHistoryWord() {
        return historyWord;
    }



    public void saveWordToHistory() throws IOException {
        File file = new File(HISTORYWORD_PATH);
        FileWriter writer = new FileWriter(file, false);
        for (Word word : historyWord) {
            writer.write(word.toString());
            writer.write('\n');
        }
        writer.close();
    }

    public void addWordToHistory(Word word) throws IOException {
        historyWord.removeIf(word1 -> {
            return word1.equals(word);
        });
        historyWord.add(0, word);
    }

    public List<Word> readHistory() {
        InputStream inputStream = HistoryManager.class.getResourceAsStream("/data/historyWord.txt");
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

