package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class WordManager {
    private List<Word> wordList;

    public WordManager() throws IOException {
        wordList = loadEngWord();
        wordList.addAll(loadViWord());
        Collections.sort(wordList);
    }



    private List<Word> loadViWord() {
        InputStream inputStream = HistoryManager.class.getResourceAsStream("/data/wordVi.txt");
        String[] newWord = new String[30000];
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
            if (arr.length < 3) {
                index++;
                continue;
            } else if (arr.length == 4) {
                Word newword = new Word(arr[0], arr[1], arr[2], arr[3]);
                System.out.println(newword);
                word.add(newword);
            }
            else {
                Word word1 = new Word(arr[0], arr[1], arr[2], "");
                System.out.println(word1);
                word.add(word1);
            }
            index++;
        }
        return word;
    }

    private List<Word>  loadEngWord() throws IOException {
        InputStream inputStream = HistoryManager.class.getResourceAsStream("/data/wordEn.txt");
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
            if (arr.length < 3) {
                continue;
            } else if (arr.length == 4) {
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
