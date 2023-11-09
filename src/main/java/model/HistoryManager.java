package model;

import java.io.*;


public class HistoryManager extends Manager{
    private static HistoryManager historyManager;
    private final String HISTORYWORD_PATH = "src/main/resources/data/wordHistory.dat";

    public static HistoryManager getHistoryManager() throws IOException {
        if (historyManager == null) {
            historyManager = new HistoryManager();
        }
        return historyManager;
    }

    private HistoryManager() throws IOException {
        super();
        read(HISTORYWORD_PATH);
        updateWordSpelling();
    }

    @Override
    public void save() throws IOException {
        super.save(HISTORYWORD_PATH);
    }


}

