package jflat;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestTrie {
    public static void main(String[] args) {
        ObservableList<String> word = FXCollections.observableArrayList();
        Database db = new Database();
        Trie trie = new Trie();
        db.listAV(word);
        for(String t : word){
            trie.insert(t);
        }
        ObservableList<String> fwobl = trie.findWords("se");
        for(String t : fwobl){
            System.out.println(t);
        }
        System.out.println();
        trie.insert("abcdefgh");
        fwobl = trie.findWords("abcdefgh");
        for(String t : fwobl){
            System.out.println(t);
        }
    }
}