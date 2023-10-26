package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class WordManager {
    private List<Word> engWord;

    public WordManager() throws IOException {
        engWord = loadEngWord();
    }

    private List<Word>  loadEngWord() throws IOException {
        InputStream inputStream = HistoryManager.class.getResourceAsStream("/data/word.txt");
        String[] newWord = new String[120000];
        if (inputStream == null) {
            System.err.println("File not found.");
            return new ArrayList<>();
        }

        Scanner scanner = new Scanner(inputStream);
        int count = 0;
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.isEmpty()) {
                continue;
            }
            newWord[count] = s;
            count++;
        }
        List<Word> word = new ArrayList<>(count);
        int index = 0;
        while (newWord[index] != null) {
            String[] arr = newWord[index].split("\\? ");
            if (arr.length == 4) {
                word.add(new Word(arr[0], arr[1].replaceAll("\\^", "'"), arr[2], arr[3].replaceAll("\\^", "'")));
            }
            else {
                word.add(new Word(arr[0], arr[1].replaceAll("\\^", "'"), arr[2], ""));
            }
            index++;
        }
        return word;
    }

    public int binarySearchWordOnly(String s) {
        return Collections.binarySearch(engWord, new Word(s, "", "", ""));
    }

    public Word getEngWordIndex(int id) {
        return engWord.get(id);
    }

    public int binarySearchWordList(String s) {
        int l = 0, r = engWord.size() - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;

            if (engWord.get(m).getSpelling().indexOf(s) == 0) {
                return m;
            }

            if (engWord.get(m).getSpelling().compareTo(s) < 0) {
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
            while (up >= 0 && index - up < 10 && engWord.get(up).getSpelling().contains(s)) {
                arr.add(engWord.get(up).getSpelling());
                up--;
            }
            while (down < engWord.size() && down - index < 10 && engWord.get(down).getSpelling().contains(s)) {
                arr.add(engWord.get(down).getSpelling());
                down++;
            }
        }
        return new ArrayList<>(arr);
    }
}
