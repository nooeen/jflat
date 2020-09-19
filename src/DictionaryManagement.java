import java.util.Scanner;

public class DictionaryManagement {

    private String english = "";
    private String vietnamese = "";

    /**
     * insert word into the dictionary.
     * @param dict the dictionary's object
     */
    public void insertFromCommandLine(Dictionary dict) {
        Scanner input = new Scanner(System.in);
        english = input.nextLine();
        vietnamese = input.nextLine();
        dict.wordsList.put(english, vietnamese);
    }
}
