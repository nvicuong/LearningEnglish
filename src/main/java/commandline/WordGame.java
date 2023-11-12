package commandline;

import java.io.*;
import java.util.ArrayList;

public class WordGame {
    private static final String path = "/data/ontap.txt";
    private ArrayList<String> words = new ArrayList<String>();
    public WordGame() {
        try (InputStream in = getClass().getResourceAsStream(path)) {
            if (in != null) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = bf.readLine()) != null)
                    words.add(line);
            } else {
                System.out.println("Couldn't find/read file: " + path);
            }
        } catch (Exception e) {
            System.out.println("Couldn't find/read file: " + path);
            System.out.println("Error message: " + e.getMessage());
        }
    }
    public String getRandomWord() {
        if (words.isEmpty()) return "NO-DATA";
        return words.get((int)(Math.random()*words.size()));
    }
}