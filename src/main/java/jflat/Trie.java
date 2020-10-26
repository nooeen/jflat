package jflat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Trie {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("đấy ");
        System.out.println(trie.search("đấy "));   // returns true
        System.out.println(trie.search("app"));     // returns false
        System.out.println(trie.startsWith("a")); // returns true
        trie.insert("aalo ");
        System.out.println(trie.search("app"));     // returns true
        System.out.println(trie.findWords("ap")); // [app, apple]
        trie.insert("atom");
        System.out.println(trie.findWords("a")); // [app, apple, atom]
        System.out.println(trie.search(" ")); // [app, apple, atom]
    }

    TrieNode root;

    /**
     * initialize Trie root
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * insert words into Trie
     * @param word  inserted words
     */
    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode());
            }
            cur = cur.children.get(c);
            cur.words.add(word);
        }
        cur.isWord = true;
    }

    /**
     * return if the words is in Trie
     * @param word the word need to search for
     * @return isWord to check true or false
     */
    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if(!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }
        return cur.isWord;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     * @param prefix the prefix of words
     * @return if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for(char c : prefix.toCharArray()) {
            if(!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }

        return true;
    }

    /**
     * find all words started with the given prefix.
     * @param prefix prefix of the words need to find
     * @return list of words start with the given prefix
     */
    public ObservableList<String> findWords(String prefix) {
        TrieNode cur = root;
        for(char c : prefix.toCharArray()) {
            if(!cur.children.containsKey(c)) {
                return FXCollections.observableArrayList();
            }
            cur = cur.children.get(c);
        }
        return cur.words;
    }
}
