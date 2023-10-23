package model;

import java.util.Comparator;

public class Word implements Comparable<Word>{
    private String spelling;
    private String pronunciation;
    private String content;
    private String synonym;

    public Word() {
    }


    public Word(String spelling, String pronunciation, String content, String synonym) {
        this.spelling = spelling;
        this.pronunciation = pronunciation;
        this.synonym = synonym;
        this.content = content;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String pos) {
        this.content = content;
    }



    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    @Override
    public String toString() {
        return "WordDAO{" +
                "spelling='" + spelling + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                ", content='" + content + '\'' +
                ", synonym='" + synonym + '\'' +
                '}';
    }

    /**
     * @param o the object to be compared.
     * @return
     */

    /**
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Word o) {
        return Comparator.comparing(Word::getSpelling).compare(this, o);
    }
}
