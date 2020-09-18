import java.io.*;
import java.util.*;

public class DictionaryCommandLine {
    public void showAllWords(Dictionary dic) {
        //in ra số thứ tự, từ tiếng anh và tiếng việt
        DictionaryManagement dicM = new DictionaryManagement();
        System.out.print("No | English | Vietnamese");
        for(int i = 0;i < dicM.getSize();i++) {
            System.out.print(i + 1);
            dic.newWord[i].print();
        }
    }
}
