package jflat;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TerminalConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class JFlatController implements Initializable {
    public Database dictDB = new Database();

    public GCloudTTS tts = new GCloudTTS();

    public ObservableList<String> words = FXCollections.observableArrayList();
    public ObservableList<String> favWords = FXCollections.observableArrayList();
    public ObservableList<String> historyWords = FXCollections.observableArrayList();

    public boolean isDark = true;

    public boolean isAV = true;

    public boolean isHome = true;
    public boolean isFav = false;
    public boolean isHistory = false;
    public boolean isTranslate = false;
    public boolean isTerminal = false;
    public boolean isSettings = false;
    public boolean isAddUpdate = false;

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
        translateField.setVisible(false);
        listPane.setVisible(false);
        webPane.setVisible(false);
        settingsPane.setVisible(false);
        addUpdatePane.setVisible(false);
        translatePane.setVisible(false);
        terminalPane.setVisible(false);
    }

    public void initTerminal() {
        terminalPane.setVisible(false);
        TerminalConfig config = new TerminalConfig();
        config.setBackgroundColor(Color.rgb(29,29,29));
        config.setForegroundColor(Color.WHITE);
        TerminalBuilder terminalBuilder = new TerminalBuilder(config);
        TerminalTab terminal = terminalBuilder.newTerminal();
        terminalPane.getTabs().add(terminal);
    }

    @FXML
    public void initWordsList() {
        if (isAV) {
            dictDB.listAV(words);
        } else {
            dictDB.listVA(words);
        }
        wordsList.setItems(words);
    }

    @FXML
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
            if(!historyWords.contains(selectedWord)) {
                historyWords.add(selectedWord);
            }
            return;
        } else if (isHistory) {
            if(!dictDB.getEngDef(selectedWord).equals("")) {
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
        if(!historyWords.contains(selectedWord)) {
            historyWords.add(selectedWord);
        }
        System.gc();
    }

    @FXML
    public void getSelectedWordDef(String selectedWord) {
        if (isDark) {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("darkwebview.css").toString());
        }
        if (isFav) {
            defView.getEngine().loadContent(dictDB.getFavDef(selectedWord), "text/html");
        }
        if (isAV) {
            defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
        } else {
            defView.getEngine().loadContent(dictDB.getVieDef(selectedWord), "text/html");
        }
        System.gc();
    }

    @FXML
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
        System.gc();
    }

    @FXML
    public void addWord() {
        if(isFav) {
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

    @FXML
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
        }
        if (isAV) {
            addUpdateEditor.setHtmlText(dictDB.getEngDef(selectedWord));
        } else {
            addUpdateEditor.setHtmlText(dictDB.getVieDef(selectedWord));
        }
    }

    @FXML
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

    @FXML
    public void autoCompleteListener() {
        if(isFav) {
            dictDB.listAutoCompleteFav(favWords, autoCompleteField.getText());
            String selectedWord = "";
            if (!favWords.isEmpty()) {
                selectedWord = words.get(0);
            }
            getSelectedWordDef(selectedWord);
            return;
        }
        if (isAV) {
            dictDB.listAutoCompleteAV(words, autoCompleteField.getText());
            String selectedWord = "";
            if (!words.isEmpty()) {
                selectedWord = words.get(0);
            }
            getSelectedWordDef(selectedWord);
        } else {
            dictDB.listAutoCompleteVA(words, autoCompleteField.getText());
            String selectedWord = "";
            if (!words.isEmpty()) {
                selectedWord = words.get(0);
            }
            getSelectedWordDef(selectedWord);
        }
        System.gc();
    }

    @FXML
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

    public void handleFavBTN() {
        if (!isFav) {
            String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
            StringBuilder sb = new StringBuilder(selectedWord);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length() - 1);
            selectedWord = sb.toString();
            System.out.println(selectedWord);
            String lang = "av";
            if (!isAV) {
                lang = "va";
            }
            dictDB.addFavWord(selectedWord, lang);
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
        if(isFav) {
            handleFavoriteMenuBTN();
            return;
        }
        handleHomeMenuBTN();
    }

    public void handleSaveBTNOfHTMLEditor() {
        String word = addUpdateField.getText();
        String html = addUpdateEditor.getHtmlText();
        String type;
        if(isFav) {
            type = "fav";
        } else if(isAV) {
            type = "av";
        } else {
            type = "va";
        }
        dictDB.addWord(word, html, type);
        handleBackBTNOfHTMLEditor();
    }

    public void handleTranslateField() throws IOException {
        GCloudTranslator.translateText("en-US", translateField.getText());
        GCloudTranslator.translateText("vi-vn", translateField.getText());
    }

    public void switchUI(boolean isDark) {
        if (isDark) {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("darkwebview.css").toString());
        }
    }

    public void handleHomeMenuBTN() {
        statusReset();
        ttsBTN.setVisible(false);
        defView.getEngine().loadContent("");

        if (!isHome) {
            invisibleAll();
            autoCompleteField.setVisible(true);
            autoCompleteField.setPromptText("Search...");
            webPane.setVisible(true);
            listPane.setVisible(true);
            isHome = true;
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
        webPane.setVisible(true);
        listPane.setVisible(true);

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
        webPane.setVisible(true);
        listPane.setVisible(true);

        wordsList.setItems(historyWords);
    }

    public void handleTranslateMenuBTN() {
        statusReset();
        invisibleAll();

        translateField.setVisible(true);
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
