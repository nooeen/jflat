package jflat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class CLIDictionaryManagement {

    private String english = "";
    private String vietnamese = "";

    /**
     * insert word into the dictionary from cli.
     *
     * @param dict the dictionary's object
     */
    public void insertFromCommandLine(CLIDictionary dict) {
        Scanner input = new Scanner(System.in);
        english = input.nextLine();
        vietnamese = input.nextLine();
        dict.wordsList.put(english, vietnamese);
    }

    /**
     * insert word into the dictionary from file.
     *
     * @param dict the dictionary's object
     */
    public void insertFromFile(CLIDictionary dict) throws FileNotFoundException {
        File inputFile = new File("dictionaries.txt");
        Scanner input = new Scanner(inputFile);
        while (input.hasNext()) {
            String in = input.nextLine();
            String[] split = in.split("\t");
            english = split[0];
            vietnamese = split[1];
            dict.wordsList.put(english, vietnamese);
        }
    }

    /**
     * modify value's key in treemap.
     *
     * @param dict  the dictionary's database
     * @param value key's value
     */
    public void modifyKey(CLIDictionary dict, String value, String curKey, String newKey) {
        dict.wordsList.remove(curKey);
        dict.wordsList.put(newKey, value);
    }

    /**
     * modify key's value in treemap.
     *
     * @param dict     the dictionary's database
     * @param key      value's key
     * @param newValue key's new value
     */
    public void modifyValue(CLIDictionary dict, String key, String newValue) {
        dict.wordsList.replace(key, newValue);
    }

    /**
     * find word(s) that contain "word" string.
     *
     * @param dict the dictionary's object
     * @param word word to find
     */
    public void dictionarySearcher(CLIDictionary dict, String word) {

    }

    /**
     * export the dictionary's database to a file.
     *
     * @param dict the dictionary's database
     */
    public void dictionnaryExportToFile(CLIDictionary dict) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("export.txt");
        int i = 0;
        for (Map.Entry<String, String> entry : dict.wordsList.entrySet()) {
            i++;
            String key = entry.getKey();
            String value = entry.getValue();
            pw.printf("%-12s%-12s\n", key, value);
        }
        pw.flush();
        pw.close();
    }
}
