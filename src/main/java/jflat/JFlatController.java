package jflat;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TerminalConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class JFlatController implements Initializable {
    public Database dictDB = new Database();

    public GCloudTTS tts = new GCloudTTS();

    public ObservableList<String> words = FXCollections.observableArrayList();
    public ObservableList<String> favWords = FXCollections.observableArrayList();
    public ObservableList<String> historyWords = FXCollections.observableArrayList();
    public Trie trie;

    public boolean isDark = false;

    public boolean isAV = true;

    public boolean isHome = true;
    public boolean isFav = false;
    public boolean isHistory = false;
    public boolean isTranslate = false;
    public boolean isTerminal = false;
    public boolean isSettings = false;
    public boolean isAddUpdate = false;
    public boolean isTrie = false;

    @FXML
    public Scene mainScene;
    @FXML
    public AnchorPane dynamicPane;
    @FXML
    public AnchorPane menuPane;
    @FXML
    public AnchorPane listPane;
    @FXML
    public AnchorPane webPane;
    @FXML
    public AnchorPane settingsPane;
    @FXML
    public AnchorPane addUpdatePane;
    @FXML
    public AnchorPane translatePane;
    @FXML
    public TabPane terminalPane;
    @FXML
    public ListView<String> wordsList;
    @FXML
    public WebView defView;
    @FXML
    public HTMLEditor addUpdateEditor;
    @FXML
    public TextField autoCompleteField;
    @FXML
    public TextField addUpdateField;
    @FXML
    public TextField translateField;
    @FXML
    public TextArea engTrans;
    @FXML
    public TextArea vieTrans;
    @FXML
    public Button UIButton;
    @FXML
    public Button TrieSQLButton;
    @FXML
    public Button engTransText;
    @FXML
    public Button vieTransText;
    @FXML
    public Button favBTN;
    @FXML
    public Button switchBTN;
    @FXML
    public Button ttsBTN;
    @FXML
    public Button backBTNEditor;
    @FXML
    public Button saveBTNEditor;
    @FXML
    public Button addBTN;
    @FXML
    public Button updateBTN;
    @FXML
    public Button delBTN;
    @FXML
    public Button closeBTN;
    @FXML
    public Button minimizeBTN;
    @FXML
    public Button maximizeBTN;
    @FXML
    public Button homeMenuBTN;
    @FXML
    public Button favoriteMenuBTN;
    @FXML
    public Button historyMenuBTN;
    @FXML
    public Button translateMenuBTN;
    @FXML
    public Button terminalMenuBTN;
    @FXML
    public Button settingsMenuBTN;

    public void statusReset() {
        isHome = false;
        isFav = false;
        isHistory = false;
        isTranslate = false;
        isTerminal = false;
        isSettings = false;
        isAddUpdate = false;
    }

    public void invisibleAll() {
        autoCompleteField.setVisible(false);
        autoCompleteField.setEditable(false);
        autoCompleteField.setDisable(true);
        translateField.setVisible(false);
        translateField.setEditable(false);
        translateField.setDisable(true);
        listPane.setVisible(false);
        webPane.setVisible(false);
        settingsPane.setVisible(false);
        addUpdatePane.setVisible(false);
        translatePane.setVisible(false);
        terminalPane.setVisible(false);
    }

    public void initTerminal() {
        terminalPane.getTabs().clear();
        terminalPane.setVisible(false);
        TerminalConfig config = new TerminalConfig();
        if (isDark) {
            config.setBackgroundColor(Color.rgb(29, 29, 29));
            config.setForegroundColor(Color.WHITE);
        } else {
            config.setBackgroundColor(Color.WHITE);
            config.setForegroundColor(Color.BLACK);
        }

        TerminalBuilder terminalBuilder = new TerminalBuilder(config);
        TerminalTab terminal = terminalBuilder.newTerminal();
        terminalPane.getTabs().add(terminal);
    }

    public void initWordsList() {
        if (isAV) {
            dictDB.listAV(words);
        } else {
            dictDB.listVA(words);
        }
        wordsList.setItems(words);
    }

    public void getRandomDef() {
        ttsBTN.setVisible(true);
        if (isDark) {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("darkwebview.css").toString());
        }

        Random rand = new Random();
        String selectedWord = words.get(rand.nextInt(words.size()));
        wordsList.getSelectionModel().select(selectedWord);

        if (!historyWords.contains(selectedWord)) {
            historyWords.add(selectedWord);
        }
        defView.getEngine().loadContent(dictDB.getEngDef(selectedWord)
                + "<p></p>"
                + "<p style=\"text-align: center; \">~Word Of The Day~</p>", "text/html");
        System.gc();
    }

    public void getWordDef() {
        ttsBTN.setVisible(true);

        if (isDark) {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("darkwebview.css").toString());

        }
        String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
        StringBuilder sb = new StringBuilder(selectedWord);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        selectedWord = sb.toString();

        if (isFav) {
            defView.getEngine().loadContent(dictDB.getFavDef(selectedWord), "text/html");
            if (!historyWords.contains(selectedWord)) {
                historyWords.add(selectedWord);
            }
            return;
        } else if (isHistory) {
            if (!dictDB.getEngDef(selectedWord).equals("")) {
                defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
            } else {
                defView.getEngine().loadContent(dictDB.getVieDef(selectedWord), "text/html");
            }
            return;
        }

        if (isAV) {
            defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
        } else {
            defView.getEngine().loadContent(dictDB.getVieDef(selectedWord), "text/html");
        }
        if (!historyWords.contains(selectedWord)) {
            historyWords.add(selectedWord);
        }
        System.gc();
    }

    public void getSelectedWordDef(String selectedWord) {
        if (isDark) {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("darkwebview.css").toString());
        }
        if (isTranslate) {
            defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
            System.out.println(dictDB.getEngDef(selectedWord));
            return;
        }
        if (isFav) {
            defView.getEngine().loadContent(dictDB.getFavDef(selectedWord), "text/html");
            return;
        }
        if (isAV) {
            defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
        } else {
            defView.getEngine().loadContent(dictDB.getVieDef(selectedWord), "text/html");
        }
        System.gc();
    }

    public void switchDict() {
        if (isAV) {
            dictDB.listVA(words);
            wordsList.setItems(words);
            isAV = false;
        } else {
            dictDB.listAV(words);
            wordsList.setItems(words);
            isAV = true;
        }
        if(isTrie) {
            trie = new Trie();
            for(String t : words) {
                trie.insert(t);
            }
        }
        System.gc();
    }

    public void addWord() {
        if (isFav) {
            statusReset();
            isFav = true;
        } else {
            statusReset();
        }
        invisibleAll();

        autoCompleteField.setVisible(true);
        autoCompleteField.setPromptText("Add/Update Words...");
        addUpdatePane.setVisible(true);
        addUpdateEditor.setHtmlText("<h1>[Word's name]</h1>\n" +
                "<h3><i>[Pronounciation]</i></h3>\n" +
                "\n" +
                "<h2>[Word's type 1]</h2>\n" +
                "<ul>\n" +
                "    <li>[Meaning1]<ul style=\"list-style-type:circle\">\n" +
                "            <li>[English Example]:<i> [Vietnamese Example]</i></li>\n" +
                "        </ul>\n" +
                "    </li>\n" +
                "    <li>[Meaning2]<ul style=\"list-style-type:circle\">\n" +
                "            <li>[English Example1]:<i> [Vietnamese Example1]</i></li>\n" +
                "            <li>[English Example2]:<i> [Vietnamese Example2]</i></li>\n" +
                "        </ul>\n" +
                "    </li>\n" +
                "</ul>\n" +
                "\n" +
                "<h2>[Word's type 2]</h2>\n" +
                "<ul>\n" +
                "    <li>[Meaning1]<ul style=\"list-style-type:circle\">\n" +
                "            <li>[English Example]:<i> [Vietnamese Example]</i></li>\n" +
                "        </ul>\n" +
                "    </li>\n" +
                "    <li>[Meaning2]<ul style=\"list-style-type:circle\">\n" +
                "            <li>[English Example1]:<i> [Vietnamese Example1]</i></li>\n" +
                "            <li>[English Example2]:<i> [Vietnamese Example2]</i></li>\n" +
                "        </ul>\n" +
                "    </li>\n" +
                "</ul>");
        isAddUpdate = true;
    }

    public void updateWord() {
        statusReset();
        invisibleAll();

        autoCompleteField.setVisible(true);
        autoCompleteField.setPromptText("Add/Update Words...");
        addUpdatePane.setVisible(true);
        String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
        StringBuilder sb = new StringBuilder(selectedWord);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        selectedWord = sb.toString();
        addUpdateField.setText(selectedWord);
        isAddUpdate = true;
        if (isFav) {
            addUpdateEditor.setHtmlText(dictDB.getFavDef(selectedWord));
            dictDB.deleteFavWord(selectedWord);
        }
        if (isAV) {
            addUpdateEditor.setHtmlText(dictDB.getEngDef(selectedWord));
            dictDB.deleteEngWord(selectedWord);
        } else {
            addUpdateEditor.setHtmlText(dictDB.getVieDef(selectedWord));
            dictDB.deleteVieWord(selectedWord);
        }

    }

    public void deleteWord() {
        String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
        StringBuilder sb = new StringBuilder(selectedWord);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        selectedWord = sb.toString();
        if (isHistory) {
            historyWords.remove(selectedWord);
            wordsList.setItems(historyWords);
            return;
        }
        if (isFav) {
            dictDB.deleteFavWord(selectedWord);
            dictDB.listFav(favWords);
            wordsList.setItems(favWords);
            System.gc();
            return;
        }
        if (isAV) {
            dictDB.deleteEngWord(selectedWord);
            dictDB.listAV(words);
        } else {
            dictDB.deleteVieWord(selectedWord);
            dictDB.listVA(words);
        }
        wordsList.setItems(words);
        System.gc();
    }

    public void autoCompleteListener() {
        if (isFav) {
            if (!isTrie) {
                dictDB.listAutoCompleteFav(favWords, autoCompleteField.getText());
            } else {
                favWords = trie.findWords(autoCompleteField.getText());
                wordsList.setItems(favWords);
            }
            String selectedWord = "";
            if (!favWords.isEmpty()) {
                selectedWord = words.get(0);
            }
            getSelectedWordDef(selectedWord);
            return;
        }
        if (isAV) {
            if (!isTrie) {
                dictDB.listAutoCompleteAV(words, autoCompleteField.getText());
            } else {
                words = trie.findWords(autoCompleteField.getText());
                wordsList.setItems(words);
            }
            String selectedWord = "";
            if (!words.isEmpty()) {
                selectedWord = words.get(0);
            }
            getSelectedWordDef(selectedWord);
        } else {
            if (!isTrie) {
                dictDB.listAutoCompleteVA(words, autoCompleteField.getText());
            } else {
                words = trie.findWords(autoCompleteField.getText());
                wordsList.setItems(words);
            }
            String selectedWord = "";
            if (!words.isEmpty()) {
                selectedWord = words.get(0);
            }
            getSelectedWordDef(selectedWord);
        }
        System.gc();
    }

    public void ttsPlay() throws Exception {
        String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
        StringBuilder sb = new StringBuilder(selectedWord);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        selectedWord = sb.toString();
        if (isAV && !selectedWord.equals("")) {
            tts.mp3("en-US", selectedWord);
            Media output = new Media(new File("ttsOutput.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(output);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        } else if (!isAV && !selectedWord.equals("")) {
            tts.mp3("vi-VN", selectedWord);
            Media output = new Media(new File("ttsOutput.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(output);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        }
        System.gc();
    }

    public void handleUISwitch() {
        mainScene = UIButton.getScene();
        isDark = !isDark;
        if (isDark) {
            mainScene.getStylesheets().remove(getClass().getResource("style.css").toExternalForm());
            mainScene.getStylesheets().add(getClass().getResource("darkstyle.css").toExternalForm());
        } else {
            mainScene.getStylesheets().remove(getClass().getResource("darkstyle.css").toExternalForm());
            mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        }
        switchUI(isDark);
    }

    public void handleTrieSQLSwitch() {
        isTrie = !isTrie;
        System.out.println(isTrie);
    }

    public void handleFavBTN() {
        dictDB.listFav(favWords);
        if (!isFav) {
            String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
            StringBuilder sb = new StringBuilder(selectedWord);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length() - 1);
            selectedWord = sb.toString();
            String lang = "av";
            if (!isAV) {
                lang = "va";
            }
            if (!favWords.contains(selectedWord)) {
                dictDB.addFavWord(selectedWord, lang);
                favWords.add(selectedWord);
            }
        }
    }

    public void handleCloseBTN(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleMinimizeBTN(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleBackBTNOfHTMLEditor() {
        if (isFav) {
            handleFavoriteMenuBTN();
            return;
        }
        handleHomeMenuBTN();
    }

    public void handleSaveBTNOfHTMLEditor() {
        String word = addUpdateField.getText();
        String html = addUpdateEditor.getHtmlText();
        String type;
        if (isFav) {
            type = "fav";
        } else if (isAV) {
            type = "av";
        } else {
            type = "va";
        }
        dictDB.addWord(word, html, type);
        handleBackBTNOfHTMLEditor();
    }

    public void handleTranslateField() throws Exception {
        String GCloudEng = GCloudTranslator.translateText("en-US", translateField.getText());
        String GCloudVie = GCloudTranslator.translateText("vi-vn", translateField.getText());

        engTrans.setText(GCloudEng);
        vieTrans.setText(GCloudVie);

        defView.getEngine().loadContent(dictDB.getEngDef(translateField.getText()), "text/html");
        webPane.setVisible(true);

        if (!historyWords.contains(GCloudEng)) {
            historyWords.add(GCloudEng);
        }
    }

    public void switchUI(boolean isDark) {
        if (isDark) {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("darkwebview.css").toString());
        } else {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("webview.css").toString());
        }
        initTerminal();
    }

    public void handleHomeMenuBTN() {
        statusReset();
        ttsBTN.setVisible(false);
        defView.getEngine().loadContent("");

        if (!isHome) {
            invisibleAll();
            autoCompleteField.setVisible(true);
            autoCompleteField.setPromptText("Search...");
            autoCompleteField.setEditable(true);
            autoCompleteField.setDisable(false);
            webPane.setVisible(true);
            listPane.setVisible(true);
            isHome = true;
        }

        //chose search method

        if(isTrie){
            trie = new Trie();
            if(isAV){
                dictDB.listAV(words);
            } else {
                dictDB.listVA(words);
            }
            for(String t : words){
                trie.insert(t);
            }
        }

        initWordsList();
    }

    public void handleFavoriteMenuBTN() {
        statusReset();
        invisibleAll();

        isHome = true;
        isFav = true;

        defView.getEngine().loadContent("");
        ttsBTN.setVisible(false);
        autoCompleteField.setVisible(true);
        autoCompleteField.setPromptText("Search...");
        autoCompleteField.setEditable(true);
        autoCompleteField.setDisable(false);
        webPane.setVisible(true);
        listPane.setVisible(true);

        if(isTrie){
            trie = new Trie();
            dictDB.listFav(favWords);
            for( String t : favWords){
                trie.insert(t);
            }
            System.out.println("trie charged");
        }

        dictDB.listFav(favWords);
        wordsList.setItems(favWords);
    }

    public void handleHistoryMenuBTN() {
        statusReset();
        invisibleAll();

        isHistory = true;

        defView.getEngine().loadContent("");
        ttsBTN.setVisible(false);
        autoCompleteField.setVisible(true);
        autoCompleteField.setPromptText("Search...");
        autoCompleteField.setEditable(true);
        autoCompleteField.setDisable(false);
        webPane.setVisible(true);
        listPane.setVisible(true);

        wordsList.setItems(historyWords);
    }

    public void handleTranslateMenuBTN() {
        statusReset();
        invisibleAll();

        engTrans.setEditable(false);
        vieTrans.setEditable(false);
        translateField.setVisible(true);
        translatePane.setVisible(true);
        translateField.setEditable(true);
        translateField.setDisable(false);
        isTranslate = true;
    }

    public void handleTerminalMenuBTN() {
        statusReset();
        invisibleAll();

        terminalPane.setVisible(true);
        isTerminal = true;
    }

    public void handleSettingsMenuBTN() {
        statusReset();
        invisibleAll();

        settingsPane.setVisible(true);
        isSettings = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWordsList();
        initTerminal();
        getRandomDef();
        switchUI(isDark);

        try {
            tts.mp3("en-US", "Welcome to JFlat!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Media output = new Media(new File("ttsOutput.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(output);
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.play();
    }


}
