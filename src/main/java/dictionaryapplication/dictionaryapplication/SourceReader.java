package dictionaryapplication.dictionaryapplication;

import java.io.InputStream;
import java.util.Scanner;

import dictionaryapplication.dictionaryapplication.data.*;

class Cache {
    public static Word lastWord = null;
    public static WordContent lastContent = null;
    public static RefWord lastRefWord = null;

    public static void updateRefWord() {
        if (lastRefWord != null) {
            lastWord.addContent(lastRefWord);
            lastRefWord = null;
        }
    }

    public static void updateContent() {
        if (lastContent != null) {
            lastWord.addContent(lastContent);
            lastContent = null;
        }
    }

    public static void updateWord() {
        updateContent();
        updateRefWord();
        if (lastWord != null) {
            Dictionary.addWord(lastWord);
            lastWord = null;
        }
    }
}

public class SourceReader {
    public static void start() {
        InputStream inputStream = SourceReader.class.getResourceAsStream("/dictionaryapplication/wordSource/anh-viet.txt");
        if (inputStream == null) {
            System.err.println("File not found.");
            return;
        }

        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            try {
                if (line.startsWith("\uFEFF@") || line.startsWith("@")) {
                    Cache.updateWord();

                    int posL = line.indexOf(" /");
                    int posR = line.indexOf('/', posL + 2);
                    if (posL == -1 || posR == -1) {
                        String spell = line.substring(1);
                        Cache.lastWord = new Word(spell, "");
                    } else {
                        String spell = line.substring(1, posL);
                        String pronun = line.substring(posL+1, posR+1);

                        Cache.lastWord = new Word(spell, pronun);

                        while (true) {
                            posL = line.indexOf(" (", posR);
                            if (posL == -1) break; posL++;

                            posR = line.indexOf(')', posL);
                            spell = line.substring(posL + 1, posR);

                            posL = line.indexOf(" /", posR);
                            if (posL == -1) {
                                Cache.lastWord.addSynonym(spell, "");
                            } else {
                                posR = line.indexOf('/', posL + 2);

                                pronun = line.substring(posL+1, posR + 1);
                                Cache.lastWord.addSynonym(spell, pronun);
                            }
                        }
                    }
                }
                else if (line.startsWith("*")) {
                    Cache.updateContent();

                    String name = line.substring(2);
                    Cache.lastContent = new WordPOS(name);
                }
                else if (line.startsWith("!")) {
                    Cache.updateRefWord();

                    String phrase = line.substring(1);
                    Cache.lastRefWord = new RefWord(phrase);
                }
                else if (line.startsWith("-")) {
                    String desc = line.substring(1);
                    WordContent val = new WordDef(desc);
                    if (Cache.lastRefWord != null) {
                        Cache.lastRefWord.addContent(val);
                    } else {
                        Cache.updateContent();
                        Cache.lastContent = val;
                    }
                }
                else if (line.startsWith("=")) {
                    int pos = line.indexOf('+');
                    WordContent val;

                    if (pos == -1 || pos == line.length()-1) {
                        String phrase = line.substring(1);
                        val = new WordPhrase(phrase, "");
                    } else {
                        String phrase = line.substring(1, pos);
                        String desc = line.substring(pos + 2);
                        val = new WordPhrase(phrase, desc);
                    }
                    if (Cache.lastRefWord != null) {
                        Cache.lastRefWord.addContent(val);
                    } else {
                        Cache.updateContent();
                        Cache.lastContent = val;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        // Close the Scanner
        scanner.close();
    }
}

