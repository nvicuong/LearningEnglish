package dictionaryapplication.dictionaryapplication.data;

public class WordPhrase extends WordContent {
    private final String phrase;
    private final String desc;

    public String getPhrase() {
        return phrase;
    }

    public String getDesc() {
        return desc;
    }

    public WordPhrase(String phrase, String desc) {
        this.phrase = phrase;
        this.desc = desc;
    }


    @Override
    public String toString() {
        String s = "phrase=%s,desc=%s\n";
        return String.format(s, phrase, desc);
    }
}
