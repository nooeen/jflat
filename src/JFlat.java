public class JFlat {
    /**
     * the main program.
     * @param args default args
     */
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        DictionaryManagement dictMgmt = new DictionaryManagement();
        DictionaryCommandLine dictCli = new DictionaryCommandLine();
        dictCli.dictionaryBasic(dict, dictMgmt);
    }
}
