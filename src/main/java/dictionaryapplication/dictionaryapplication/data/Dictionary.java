package dictionaryapplication.dictionaryapplication.data;

import java.util.ArrayList;

public class Dictionary {
    private static ArrayList<Word> content = new ArrayList<>();

    public static void addWord(Word w) {
        content.add(w);
    }

    public static int getSize() {
        return content.size();
    }
    public static Word getWord(int index) {
        return content.get(index);
    }

    public static ArrayList<Word> fetchAll() { return content; }
}
