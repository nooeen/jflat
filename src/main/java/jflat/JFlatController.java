package jflat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class JFlatController implements Initializable {
    public Database dictDB = new Database();

    public GCloudTTS tts = new GCloudTTS();

    public ObservableList<String> words = FXCollections.observableArrayList();
    public ObservableList<String> favWords = FXCollections.observableArrayList();

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
        listPane.setVisible(false);
        webPane.setVisible(false);
        settingsPane.setVisible(false);
        addUpdatePane.setVisible(false);
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
            return;
        } else if (isHistory) {
            return;
        }

        if (isAV) {
            defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
        } else {
            defView.getEngine().loadContent(dictDB.getVieDef(selectedWord), "text/html");
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
        isAddUpdate = true;
    }

    @FXML
    public void updateWord() {
        statusReset();
        invisibleAll();

        autoCompleteField.setVisible(true);
        autoCompleteField.setPromptText("Add/Update Words...");
        addUpdatePane.setVisible(true);
        isAddUpdate = true;
    }

    @FXML
    public void deleteWord() {
        String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
        StringBuilder sb = new StringBuilder(selectedWord);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        selectedWord = sb.toString();
        if (isFav) {
            dictDB.deleteFavWord(selectedWord);
            dictDB.listFav(words);
            wordsList.setItems(words);
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
        String type = "";
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
    }

    public void handleTranslateMenuBTN() {
        statusReset();
        invisibleAll();

        autoCompleteField.setVisible(true);
        autoCompleteField.setPromptText("Search...");
        isTranslate = true;
    }

    public void handleTerminalMenuBTN() {
        statusReset();
        invisibleAll();

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
        switchUI(isDark);
    }


}
