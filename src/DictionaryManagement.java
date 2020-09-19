import java.util.Scanner;

public class DictionaryManagement {

    private String english = "";
    private String vietnamese = "";
    private int size;

    /**
     * insert word into the dictionary.
     * @param dict the dictionary's object
     */
    public void insertFromCommandline(Dictionary dict) {
        Scanner sc = new Scanner(System.in);
        size = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < size; i++) {
            english = sc.nextLine();
            vietnamese = sc.nextLine();
            dict.newWord[i] = new Word(english, vietnamese);
        }
    }

    /**
     * get dictionary's size
     * @return dictionary's size
     */
    public int getSize() {
        return size;
    }
}
