package model;

import help.Help;

import java.io.*;
import java.util.*;


public class HistoryManager {

    private List<Word> historyWord;

    private List<String> historySpelling;
    private final String HISTORYWORD_PATH = "src\\\\main\\\\resources\\\\data\\\\wordHistory.dat";

    public HistoryManager() throws IOException {
        historySpelling = new ArrayList<>();
        historyWord = new ArrayList<>();
        readHistory();
        updateHistorySpelling();
    }

    public List<Word> getHistoryWord() {
        return historyWord;
    }

    public List<String> getHistorySpelling() {
        updateHistorySpelling();
        return historySpelling;
    }

    public void updateHistorySpelling() {
        historySpelling.clear();
        for (Word word : historyWord) {
            historySpelling.add(word.getSpelling());
        }
    }


    public void saveWordToHistory() throws IOException {
        try {
            FileOutputStream fileInputStream = new FileOutputStream(HISTORYWORD_PATH);
            ObjectOutputStream os = new ObjectOutputStream(fileInputStream);
            if (!historyWord.isEmpty()) {
                for (Word word : historyWord) {
                    os.writeObject(word);
                }
            }
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWordToHistory(Word word) throws IOException {
        historyWord.removeIf(word1 -> {
            return word1.equals(word);
        });
        historyWord.add(0, word);
        updateHistorySpelling();
    }

    public void readHistory() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(HISTORYWORD_PATH);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Word w = null;
        try {
            while ((w = (Word) objectInputStream.readObject()) != null) {
                historyWord.add(w);
            }
        } catch (EOFException e) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        objectInputStream.close();
    }
}

