package jflat;

import java.util.List;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.collections.FXCollections;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        Database data = new Database();
        ObservableList<String> List = FXCollections.observableList(list);
        data.listAV(List);
        Trie trie = new Trie();
        int c = 0;
        for (String t : List) {
            trie.insert(t);
        }
        System.out.println(trie.findWords("f"));
    }
}
