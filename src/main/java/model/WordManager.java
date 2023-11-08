package model;

import help.Help;
import javafx.concurrent.Task;

import java.io.*;
import java.util.*;

public class WordManager extends Manager{

    private static WordManager wordManager;
    private final String WORD_PATH = "src/main/resources/data/word.dat";

    public static WordManager getWordManager() throws IOException, ClassNotFoundException {
        if (wordManager == null) {
            wordManager = new WordManager();
        }
        return wordManager;
    }

    private WordManager() throws IOException, ClassNotFoundException {
        super();
        read(WORD_PATH);
    }

    /**
     * @throws IOException
     */
    @Override
    public void save() throws IOException {
        super.save(WORD_PATH);
    }

    public Word getRandomWord() {
        Random random = new Random();
        int number = random.nextInt(wordList.size() - 1);
        return wordList.get(number);
    }

    public int binarySearchWordOnly(String s) {
        return Collections.binarySearch(wordList, new Word(s, "", "", ""));
    }

    public Word getEngWordIndex(int id) {
        return wordList.get(id);
    }

    public int binarySearchWordList(String s) {
        int l = 0, r = wordList.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            if (wordList.get(m).getSpelling().indexOf(s) == 0) {
                return m;
            }

            if (wordList.get(m).getSpelling().compareTo(s) < 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        return -1;
    }

    public List<String> searchWordList(String s) {
        Set<String> arr = new HashSet<>();
        int index = binarySearchWordList(s);
        if (index >= 0) {
            int up = index, down = index;
            while (up >= 0 && index - up < 10 && wordList.get(up).getSpelling().contains(s)) {
                arr.add(wordList.get(up).getSpelling());
                up--;
            }
            while (down < wordList.size() && down - index < 10 && wordList.get(down).getSpelling().contains(s)) {
                arr.add(wordList.get(down).getSpelling());
                down++;
            }
        }
        return new ArrayList<>(arr);
    }
}
