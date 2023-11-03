package games;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class RunCrosswordGame {
    private static RunCrosswordGame runCrosswordGame;

    private ArrayList<ArrayList<Character>> matrix;

    private void init(int SIZE) {
        for (int i = 0; i < SIZE; i++) {
            matrix.add(new ArrayList<>());
        }
    }

    private void addToMatrix(int x, char c) {
        matrix.get(x).add(c);
    }

    private void printMatrix() {
        for (ArrayList<Character> a : matrix) {
            for (char i : a) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    private void clearMatrix(int SIZE) {
        matrix = new ArrayList<>();
        init(SIZE);
    }

    private void clearWordList() {
        wordList = new HashMap<>();
    }

    private HashMap<Pair, String> wordList;

    public static RunCrosswordGame getRunCrosswordGame() {
        if (runCrosswordGame == null) {
            runCrosswordGame = new RunCrosswordGame();
        }
        return runCrosswordGame;
    }

    public void createThread(int SIZE, int SHRT, int LONG) {
        new Thread(createTask(SIZE, SHRT, LONG)).start();

    }

    private void runCppFile(int SIZE, int SHRT, int LONG) throws IOException, InterruptedException {
        Process compileProcess = new ProcessBuilder("g++",
                "src/main/java/games/gener.cpp", "-o", "gener.exe").start();
        if (compileProcess.waitFor() != 0) {
            System.err.println("Failed to compile gener.cpp");
            return;
        }

        Process runProcess = new ProcessBuilder("./gener.exe",
                String.valueOf(SIZE), String.valueOf(SHRT), String.valueOf(LONG)).start();
        InputStream inputStream = runProcess.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        for (int i = 0; i < SIZE + SHRT + LONG; i++) {
            String line = bufferedReader.readLine();
            if (i < SHRT + LONG) {
                System.out.println(line);
                String[] lineArr = line.split(" ");
                Pair pair = new Pair(new Point(Integer.parseInt(lineArr[1]), Integer.parseInt(lineArr[2])), new Point(Integer.parseInt(lineArr[3]), Integer.parseInt(lineArr[4])));
                wordList.put(pair, lineArr[0]);

            } else {
                for (char c : line.toCharArray()) {
                    addToMatrix(i - SHRT - LONG, c);
                }
            }
        }

        runProcess.waitFor();
    }

    public Task<Void> createTask(int SIZE, int SHRT, int LONG) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Thực hiện các tác vụ đồng bộ tại đây
                clearWordList();
                clearMatrix(SIZE);
                runCppFile(SIZE, SHRT, LONG);
                return null;
            }
        };
    }

    public ArrayList<ArrayList<Character>> getMatrix() {
        return matrix;
    }

    public HashMap<Pair, String> getWordList() {
        return wordList;
    }
}
