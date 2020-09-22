import java.util.LinkedList;

public class TrieNode {
    char data;
    LinkedList children;
    TrieNode parent;
    boolean isEnd;

    /**
     * Trienode's constructer with char c as the parameter.
     * @param c input characters
     */
    public TrieNode(char c) {
        data = c;
        children = new LinkedList();
        isEnd = false;
    }
}
