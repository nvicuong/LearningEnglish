package dictionaryapplication.dictionaryapplication;

import dictionaryapplication.dictionaryapplication.data.Dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class TrieNode {
    public char ch;
    public int wordIdx;
    public HashMap<Character, Integer> next;
    TrieNode(char ch) {
        wordIdx = -1;
        this.ch = ch;
        next = new HashMap<>();
    }
}

public class WordSearcher {
    private static ArrayList<TrieNode> trie;

    public static void init() {
        trie = new ArrayList<>();
        trie.add(new TrieNode('.'));

        for (int i = 0; i < Dictionary.getSize(); i++) {
            addWord(Dictionary.getWord(i).getSpelling(), i);
        }
    }

    private static void addWord(String s, int idx) {
        int now = 0;
        for (char c : s.toCharArray()) {
            if (!trie.get(now).next.containsKey(c)) {
                trie.get(now).next.put(c, trie.size());
                trie.add(new TrieNode(c));
            }
            now = trie.get(now).next.get(c);
        }
        trie.get(now).wordIdx = idx;
    }

    public static ArrayList<Integer> search(String s) {
        int now = 0;
        ArrayList<Integer> result = new ArrayList<>();

        for (char c : s.toCharArray()) {
            if (!trie.get(now).next.containsKey(c)) {
                return result;
            }
            now = trie.get(now).next.get(c);
        }

        dfs(result, now);
        return result;
    }

    private static void dfs(ArrayList<Integer> result, int idx) {
        if (trie.get(idx).wordIdx != -1) {
            result.add(trie.get(idx).wordIdx);
        }
        for (Map.Entry<Character, Integer> entry : trie.get(idx).next.entrySet()) {
            Integer val = entry.getValue();
            dfs(result, val);
        }
    }
}
