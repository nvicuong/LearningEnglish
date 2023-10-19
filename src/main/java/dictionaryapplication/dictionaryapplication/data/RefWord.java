package dictionaryapplication.dictionaryapplication.data;

import java.util.ArrayList;

public class RefWord extends WordContent {
    private final String phrase;
    private ArrayList<WordContent> content;
    public RefWord(String phrase) {
        this.phrase = phrase;
        content = new ArrayList<>();
    }

    public String getPhrase() {
        return phrase;
    }

    public void addContent(WordContent val) {
        content.add(val);
    }


    @Override
    public String toString() {
        return String.format("phrase=%s\ncontent=%s\n", phrase, content);
    }
}
