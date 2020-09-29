import java.io.FileNotFoundException;
import java.util.Map.Entry;
import java.util.Scanner;

public class DictionaryCommandLine {
    /**
     * print the words's order, english writing & vietnamese writing.
     *
     * @param dict the dictionary's object
     */
    public void showAllWords(Dictionary dict) {
        int i = 0;
        System.out.printf("%-8s%-12s%s\n", "No", "| English", "| Vietnamese");
        for (Entry<String, String> entry : dict.wordsList.entrySet()) {
            i++;
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.printf("%-8s%-12s%s\n", i, "| " + key, "| " + value);
        }
    }

    /**
     * lookup a word from its english or vietnamese meaning.
     *
     * @param dict the dictionary's object
     */
    public void dictionaryLookup(Dictionary dict) {
        Scanner input = new Scanner(System.in);
        String lookupWord = input.nextLine();
        String result = dict.wordsList.get(lookupWord);
        if (result == null) {
            for (String o : dict.wordsList.keySet()) {
                if (dict.wordsList.get(o).equals(lookupWord)) {
                    result = o;
                }
            }
        }
        System.out.println(result);
    }

    /**
     * execute JFlat's basic operations.
     *
     * @param dict     the dictionary's object
     * @param dictMgmt dictionary management's object
     */
    public void dictionaryBasic(Dictionary dict, DictionaryManagement dictMgmt) {
        Scanner input = new Scanner(System.in);
        int wordsNum = input.nextInt();
        for (int i = 0; i < wordsNum; i++) {
            dictMgmt.insertFromCommandLine(dict);
        }
        showAllWords(dict);
    }

    /**
     * execute JFlat's advanced operations.
     *
     * @param dict     the dictionary's object
     * @param dictMgmt dictionary management's object
     * @throws FileNotFoundException
     */
    public void dictionaryAdvanced(Dictionary dict, DictionaryManagement dictMgmt) throws FileNotFoundException {
        dictMgmt.insertFromFile(dict);
        showAllWords(dict);
        //dictionaryLookup(dict);
    }
}
