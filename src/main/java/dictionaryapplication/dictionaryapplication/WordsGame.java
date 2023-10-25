package dictionaryapplication.dictionaryapplication;

import java.io.*;
import java.util.ArrayList;

public class WordsGame {

    private static final String fileName = "res\\words.txt";

    private ArrayList<String> words = new ArrayList<String>();

    public WordsGame() {
        try (InputStream in = getClass().getResourceAsStream(fileName);
             BufferedReader bf = new BufferedReader(new InputStreamReader(in))) {

            String line = "";
            while ((line = bf.readLine()) != null)
                words.add(line);
        }
        catch (Exception e) {
            System.out.println("Couldn't find/read file: " + fileName);
            System.out.println("Error message: " + e.getMessage());
        }
    }

    public String getRandomWord() {
        if (words.isEmpty()) return "NO-DATA";
        return words.get((int)(Math.random()*words.size()));
    }

}
