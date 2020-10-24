package jflat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
    // TrieNode[] children;
    Map<Character, TrieNode> children;
    boolean isWord;
    List<String> words;

    public TrieNode() {
        // children = new TrieNode[143859];
        children = new HashMap<>();
        isWord = false;
        words = new ArrayList<>();
    }
}
