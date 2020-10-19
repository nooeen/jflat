package jflat;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {
    TrieNode[] children;
    boolean isWord;
    List<String> words;

    public TrieNode() {
        children = new TrieNode[256];
        isWord = false;
        words = new ArrayList<>();
    }
}
