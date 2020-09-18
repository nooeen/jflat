import java.io.*;
import java.util.*;

public class MainTest {
    public static void main(String[] args) {
        /*
        hàm main
        dic - class Dictionary - mảng lưu từ
        dicM - class DictionaryManagement - nhập vào từ cmdLine
        dicCmd - class DictionaryCommandLine - showAllWords
         */
        Dictionary dic = new Dictionary();
        DictionaryManagement dicM = new DictionaryManagement();
        dicM.insertFromCommandline(dic);
        DictionaryCommandLine dicCmd = new DictionaryCommandLine();
        //showAllWords ở trong dicCmd nên gọi nó ra để in
        dicCmd.showAllWords(dic);
    }
}
