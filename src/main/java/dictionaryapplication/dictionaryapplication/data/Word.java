package dictionaryapplication.dictionaryapplication.data;

import javafx.util.Pair;
import java.util.ArrayList;

public class Word {
    private final String spelling;
    private final String pronunciation;
    private ArrayList<WordContent> content;
    private ArrayList<Pair<String, String>> synonyms;

    public String getSpelling() {
        return spelling;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public Word(String spelling, String pronunciation) {
        this.spelling = spelling;
        this.pronunciation = pronunciation;
        content = new ArrayList<>();
        synonyms = new ArrayList<>();
    }

    public void addContent(WordContent val) {
        content.add(val);
    }

    public ArrayList<WordContent> getContent() {
        return content;
    }

    public void addSynonym(String spell, String pronun) {
        synonyms.add(new Pair<>(spell, pronun));
    }

    @Override
    public String toString() {
        String s = "spelling=%s, content=%s\n";
        return String.format(s, spelling, content);
    }
}


