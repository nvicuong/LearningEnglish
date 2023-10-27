package help;

import model.Word;

public class Help {
    public static Word formatWord(Word word) {
        String spelling = word.getSpelling().replaceAll("\\^", "'");
        String pronunciation = word.getPronunciation().replaceAll("\\^", "'");
        String synonym = word.getSynonym().replaceAll("\\^", "'");
        String[] arr = word.getContent().replaceAll("\\^", "'").split("\\|");
        String content = "";
        for (String s : arr) {
            String[] defi = s.split("\\\\n");
            for (String i : defi) {
                if (i.equals(", ") || i.isBlank()) {
                    continue;
                }
                String sub = i;
                if (i.indexOf(", ") == 0 && i.length() > 2) {
                    sub = i.substring(1, i.length() - 1);
                }
                if (sub.lastIndexOf(", ") == sub.length() - 2 && sub.length() >= 2) {
                    sub = sub.substring(0, sub.length() - 2);
                }
                content += sub;
                content += "\n";
            }
        }
        return new Word(spelling, pronunciation, content, synonym);
    }

    public static Word unformatWord(Word word) {
        String spelling = word.getSpelling();
        String pronunciation = word.getPronunciation();
        String synonym = word.getSynonym();
        String content = word.getContent().replaceAll("<", "|<").replaceAll("\n", "\\\\n");
        return new Word(spelling, pronunciation, content, synonym);
    }
}
