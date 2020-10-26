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

    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts a word into the trie.
     * @param word inserted word
     */
    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode());
            }
            // cur = cur.children[c];
            cur = cur.children.get(c);
            cur.words.add(word);
        }
        cur.isWord = true;
    }

    /**
     * Returns if the word is in the trie.
     * @param word word
     * @return if the word is in the trie
     */
    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            // if(cur.children[c] == null) {
            //     return false;
            // }
            // cur = cur.children[c];
            if(!cur.children.containsKey(c)) {
                return false;
            }
            cur = cur.children.get(c);
        }
        return cur.isWord;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     * @param prefix word's prefix
     * @return words with the given prefix
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

/*
apple, app, atom
a: [apple, app, atom]
ap: [apple, app]
app: [apple, app]
appl: [apple]
appple: [apple]
at: [atom]
ato: [atom]
atom: [atom]
search (read):
- Trie: O(len(prefix))
- Hash Map: average O(1)
insert (write):
- Trie faster than Hash Map
memory used:
- trie more efficient
trie = new Trie()
dictionary = new HashMap()
main() {
  populateSearchData()
  populateTranslationData()
}
populateTranslationData() {
  for (englishword, vietnamword : words) {
    dictionary.put(englishword, vietnamword)
  }
}
populateSearchData() {
  words = openfile("words.txt")
  for (EnglishWord : words) {
    trie.insert(EnglishWord)
  }
  for (word : words) {
    for (prefix : word) {
      if !map.containsKey(prefix)
        map.put(prefix, new...)
      map.get(prefix).add(word);
    }
  }
}
translate(EnglishWord) {
  return dictionary.get(EnglishWord)
}
autocompleteSearch(prefix) {
  return trie.findWords(prefix);
  return map.get(prefix);
}*/