import java.io.*;
import java.util.*;

public class Word {
    //khỏi tạo 2 string wordTarget và wordExplain
    private String wordTarget;
    private  String wordExplain;

    //getter và setter
    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public Word(String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public Word() {
        wordTarget = "";
        wordExplain = "";
    }

    //in ra
    void print() {
        System.out.println("|     " + wordTarget + "     | " + wordExplain);
    }
}
