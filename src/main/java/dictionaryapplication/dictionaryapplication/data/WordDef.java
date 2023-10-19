package dictionaryapplication.dictionaryapplication.data;

public class WordDef extends WordContent {
    private final String desc;

    public String getDesc() {
        return desc;
    }

    public WordDef(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        String s = "desc=%s\n";
        return String.format(s, desc);
    }
}