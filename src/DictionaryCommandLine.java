public class DictionaryCommandLine {
    /**
     * print the words's order, english writing & vietnamese writing.
     * @param dict the dictionary's object
     * @param dictMgmt the dictionary management's object
     */
    public void showAllWords(Dictionary dict, DictionaryManagement dictMgmt) {
        System.out.println("No | English | Vietnamese");
        for (int i = 0; i < dictMgmt.getSize(); i++) {
            System.out.print(i + 1);
            dict.newWord[i].print();
        }
    }

    /**
     * execute JFlat's basic operations.
     * @param dict
     * @param dictMgmt
     */
    public void dictionaryBasic(Dictionary dict, DictionaryManagement dictMgmt) {
        dictMgmt.insertFromCommandline(dict);
        showAllWords(dict, dictMgmt);
    }
}
