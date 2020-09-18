import java.io.*;
import java.util.*;

public class DictionaryManagement {
    /*tạo các biến trong đó english là từ tiếng anh nhập vào, vietnamese là từ tiếng việt nhập vào
    english là từ tiếng anh ban đầu, vietnamese là từ tv dịch nghĩa english
    size là số cặp từ nhập vào từ cmdline
    */
    private String english = "";
    private String vietnamese = "";
    private int size;

    public void insertFromCommandline(Dictionary dic) {
        Scanner sc = new Scanner(System.in);
        size = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < size; i++) {
            english = sc.nextLine();
            vietnamese = sc.nextLine();
            //ném từ vào mảng newWord
            dic.newWord[i] = new Word(english, vietnamese);
            //dic.newWord[i].print();
        }
    }

    //do Dictionary Commnadline cần dùng cái size nên t cho getSize vào

    public int getSize() {
        return size;
    }
}
