package jflat;

import java.util.*;

public class Trie {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("apple");
        System.out.println(trie.search("apple"));   // returns true
        System.out.println(trie.search("app"));     // returns false
        System.out.println(trie.startsWith("a")); // returns true
        trie.insert("app");
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

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) { // apple
            //System.out.println(c);
            if( c <= 256){
                if (cur.children[c] == null) {
                    cur.children[c] = new TrieNode();
                }
                cur = cur.children[c];
                cur.words.add(word);
            }

        }
        cur.isWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if(cur.children[c] == null) {
                return false;
            }
            cur = cur.children[c];
        }
        return cur.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for(char c : prefix.toCharArray()) {
            if(cur.children[c] == null ) {
                return false;
            }
            cur = cur.children[c];
        }

        return true;
    }

    public List<String> findWords(String prefix) {
        TrieNode cur = root;
        for(char c : prefix.toCharArray()) {
            if(cur.children[c] == null ) {
                return new ArrayList<>(); // empty list
            }
            cur = cur.children[c];
        }

        return cur.words; // list of words at that node
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