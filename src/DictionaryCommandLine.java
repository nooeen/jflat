import java.util.Map.Entry;
import java.util.Scanner;

public class DictionaryCommandLine {
    /**
     * print the words's order, english writing & vietnamese writing.
     * @param dict the dictionary's object
     */
    public void showAllWords(Dictionary dict) {
        int i = 1;
        System.out.println("No | English | Vietnamese");
        for (Entry<String, String> entry : dict.wordsList.entrySet()) {
            System.out.print(i + " ");
            i++;
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " " + value);
        }
    }

    /**
     * execute JFlat's basic operations.
     * @param dict the dictionary's object
     * @param dictMgmt dictionary management's object
     */
    public void dictionaryBasic(Dictionary dict, DictionaryManagement dictMgmt) {
        Scanner input = new Scanner(System.in);
        int wordsNum = input.nextInt();
        for(int i=0; i<wordsNum; i++) {
            dictMgmt.insertFromCommandLine(dict);
        }
        showAllWords(dict);
    }
}
