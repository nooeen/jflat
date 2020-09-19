import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryManagement {

    private String english = "";
    private String vietnamese = "";

    /**
     * insert word into the dictionary from cli.
     * @param dict the dictionary's object
     */
    public void insertFromCommandLine(Dictionary dict) {
        Scanner input = new Scanner(System.in);
        english = input.nextLine();
        vietnamese = input.nextLine();
        dict.wordsList.put(english, vietnamese);
    }

    /**
     * insert word into the dictionary from file.
     * @param dict the dictionary's object
     */
    public void insertFromFile(Dictionary dict) throws FileNotFoundException {
        File inputFile = new File("dictionaries.txt");
        Scanner input = new Scanner(inputFile);
        while(input.hasNext()) {
            String in = input.nextLine();
            String[] split = in.split("\t");
            english = split[0];
            vietnamese = split[1];
            dict.wordsList.put(english, vietnamese);
        }
    }
}
