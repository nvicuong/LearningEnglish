package dictionaryapplication.dictionaryapplication.data;

public class WordPOS extends WordContent {
    private final String desc;

    public String getDesc() {
        return desc;
    }

    public WordPOS(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return String.format("POS=%s\n", desc);
    }
}
