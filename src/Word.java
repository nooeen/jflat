public class Word {
    private String wordTarget;
    private String wordExplain;

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
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

    void print() {
        System.out.println("|     " + wordTarget + "     | " + wordExplain);
    }
}
