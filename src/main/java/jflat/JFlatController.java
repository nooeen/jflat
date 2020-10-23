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
    public ListView<String> wordsList;
    @FXML
    public WebView defView;
    @FXML
    public TextField autoCompleteField;
    @FXML
    public Button favBTN;
    @FXML
    public Button switchBTN;
    @FXML
    public Button ttsBTN;
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
    public Button translateMenuBTN;
    @FXML
    public Button terminalMenuBTN;
    @FXML
    public Button settingsMenuBTN;

    @FXML
    public void initWordsList() {
        dictDB.listAV(words);
        wordsList.setItems(words);
    }

    @FXML
    public void getWordDef() {
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

    public void switchUI(boolean isDark) {
        if (isDark) {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("darkwebview.css").toString());
        }
    }

    public void handleHomeMenuBTN() {
        isTranslate = false;
        isHistory = false;
        isFav = false;
        isSettings = false;

        defView.getEngine().loadContent("");

        if (!isHome) {
            autoCompleteField.setVisible(true);
            webPane.setVisible(true);
            listPane.setVisible(true);
            isHome = true;
        }

        if (isAV) {
            dictDB.listAV(words);
        } else {
            dictDB.listVA(words);
        }
        wordsList.setItems(words);
    }

    public void handleFavoriteMenuBTN() {
        isTranslate = false;
        isHistory = false;
        isFav = true;
        isSettings = false;
        isTerminal = false;

        defView.getEngine().loadContent("");

        if (!isHome) {
            autoCompleteField.setVisible(true);
            webPane.setVisible(true);
            listPane.setVisible(true);
            isHome = true;
        }

        dictDB.listFav(favWords);
        wordsList.setItems(favWords);
    }

    public void handleTranslateMenuBTN() {
        isHome = false;
        isFav = false;
        isHistory = false;
        isTerminal = false;
        isSettings = false;

        if (!isTranslate) {
            autoCompleteField.setVisible(true);
            webPane.setVisible(false);
            listPane.setVisible(false);
            isTranslate = true;
        }
    }

    public void handleTerminalMenuBTN() {
        isHome = false;
        isFav = false;
        isHistory = false;
        isTranslate = false;
        isSettings = false;

        if(!isTerminal) {
            isTerminal = true;
        }
    }

    public void handleSettingsMenuBTN() {
        isHome = false;
        isTranslate = false;
        isHistory = false;
        isTerminal = false;

        if (!isSettings) {
            autoCompleteField.setVisible(false);
            listPane.setVisible(false);
            webPane.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWordsList();
        switchUI(isDark);
    }


}
