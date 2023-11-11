package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Manager {
    protected List<Word> wordList;
    protected List<String> wordSpelling;

    protected Manager() {
        this.wordList = new ArrayList<Word>();
        this.wordSpelling = new ArrayList<String>();
    }

    protected void read(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Word w;
        try {
            while ((w = (Word) objectInputStream.readObject()) != null) {
                wordList.add(w);
            }
        } catch (EOFException ignored) {

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        objectInputStream.close();
    }

    protected void save(String path) throws IOException {
        try {
            FileOutputStream fileInputStream = new FileOutputStream(path);
            ObjectOutputStream os = new ObjectOutputStream(fileInputStream);
            if (!wordList.isEmpty()) {
                for (Word word : wordList) {
                    os.writeObject(word);
                }
            }
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWordSpelling() {
        wordSpelling.clear();
        for (Word word : wordList) {
            wordSpelling.add(word.getSpelling());
        }
    }

    public List<String> getWordSpelling() {
        updateWordSpelling();
        return wordSpelling;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void addWord(Word word) throws IOException {
        wordList.removeIf(word1 -> {
            return word1.equals(word);
        });
        wordList.add(0, word);
        updateWordSpelling();
    }

    public abstract void save() throws IOException;
}
