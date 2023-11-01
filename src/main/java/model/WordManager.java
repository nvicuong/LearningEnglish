package model;

import help.Help;

import java.io.*;
import java.util.*;

public class WordManager {
    private final List<Word> wordList;
    private final String WORD_PATH = "src/main/resources/data/word.dat";

    public WordManager() throws IOException, ClassNotFoundException {
        wordList = new ArrayList<>();
        readWord();
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

    public void saveWord() {
        try {
            FileOutputStream fileInputStream = new FileOutputStream(WORD_PATH);
            ObjectOutputStream os = new ObjectOutputStream(fileInputStream);
            for (Word word : wordList) {
                os.writeObject(word);
            }
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readWord() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(WORD_PATH);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Word w = null;
        try {
            while ((w = (Word) objectInputStream.readObject()) != null) {
                wordList.add(w);
            }
        } catch (EOFException e) {
        }
        objectInputStream.close();
    }
}
