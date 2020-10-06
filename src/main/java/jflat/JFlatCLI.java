package jflat;

public class JFlatCLI {
    /**
     * the main program.
     *
     * @param args default args
     */
    public static void main(String[] args) throws Exception {
        Dictionary dict = new Dictionary();
        DictionaryManagement dictMgmt = new DictionaryManagement();
        DictionaryCommandLine dictCli = new DictionaryCommandLine();
        GCloudTTS tts = new GCloudTTS();
        GCloudTranslator translator = new GCloudTranslator();
        Database dictDB = new Database();

        //dictCli.dictionaryBasic(dict, dictMgmt);
        //dictCli.dictionaryAdvanced(dict, dictMgmt);
        //dictMgmt.dictionnaryExportToFile(dict);
        //tts.mp3("vi-VN", "Xin chào JFlat");
        //translator.translateText("vi", "Hello");
        //dictDB.listAll("av");
    }
}
