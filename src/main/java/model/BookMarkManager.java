package model;

import java.io.*;
import java.util.*;

public class BookMarkManager {

    private static BookMarkManager bookMarkManager;
    private List<Word> wordBank;
    private List<String> wordBankSpelling;
    private final String BOOKMARK_PATH = "src/main/resources/data/wordBank.dat";

    public static BookMarkManager getBookMarkManager() throws IOException {
        if (bookMarkManager == null) {
            bookMarkManager = new BookMarkManager();
        }
        return bookMarkManager;
    }

    private BookMarkManager() throws IOException {
        wordBank = new ArrayList<>();
        wordBankSpelling = new ArrayList<>();
        readWordBank();
        updateWordBankSpelling();
    }


    public List<Word> getWordBank() {
        return wordBank;
    }

    public List<String> getWordBankSpelling() {
        return wordBankSpelling;
    }

    public void updateWordBankSpelling() {
        wordBankSpelling.clear();
        for (Word word : wordBank) {
            wordBankSpelling.add(word.getSpelling());
        }
    }

    public void saveWordBank() throws IOException {
        try {
            FileOutputStream fileInputStream = new FileOutputStream(BOOKMARK_PATH);
            ObjectOutputStream os = new ObjectOutputStream(fileInputStream);
            if (!wordBank.isEmpty()) {
                for (Word word : wordBank) {
                    os.writeObject(word);
                }
            }
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWordToBank(Word word) throws IOException {
        wordBank.removeIf(word1 -> {
            return word1.equals(word);
        });
        wordBank.add(0, word);
        updateWordBankSpelling();
    }

    public void readWordBank() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(BOOKMARK_PATH);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Word w = null;
        try {
            while ((w = (Word) objectInputStream.readObject()) != null) {
                wordBank.add(w);
            }
        } catch (EOFException ignored) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        objectInputStream.close();
    }
}
