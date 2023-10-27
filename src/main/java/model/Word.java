package model;

import java.util.Comparator;
import java.util.Objects;

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
        return spelling + "? " + pronunciation + "? " + content + "? " + synonym;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(spelling, word.spelling);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spelling, pronunciation, content, synonym);
    }
}
