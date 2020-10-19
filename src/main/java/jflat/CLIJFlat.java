package jflat;

public class CLIJFlat {
    /**
     * the main program.
     *
     * @param args default args
     */
    public static void main(String[] args) throws Exception {
        CLIDictionary dict = new CLIDictionary();
        CLIDictionaryManagement dictMgmt = new CLIDictionaryManagement();
        CLIDictionaryCommandLine dictCli = new CLIDictionaryCommandLine();
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
