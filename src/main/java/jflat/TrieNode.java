package jflat;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {
    TrieNode[] children;
    boolean isWord;
    List<String> words;

    public TrieNode() {
        children = new TrieNode[128];
        isWord = false;
        words = new ArrayList<>();
    }
}
