package jflat;

import com.sun.webkit.WebPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class JFlatController implements Initializable {
    public JFlat jflat = new JFlat();

    public Database dictDB = new Database();

    public GCloudTTS tts = new GCloudTTS();

    public ObservableList<String> words = FXCollections.observableArrayList();
    public ObservableList<String> favWords = FXCollections.observableArrayList();

    public boolean isAV = true;
    public boolean isFav = false;
    public boolean isHistory = false;
    public boolean isDark = false;

    @FXML
    Stage stage;
    @FXML
    Scene homeScene;
    @FXML
    Scene translateScene;
    @FXML
    Scene settingsScene;
    @FXML
    public AnchorPane menuPane;
    @FXML
    public AnchorPane webPane;
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
        } else if (isHistory) {

        }

        if (isAV == true) {
            defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
        } else if (isAV == false) {
            defView.getEngine().loadContent(dictDB.getVieDef(selectedWord), "text/html");
        }
        System.gc();
    }

    @FXML
    public void getSelectedWordDef(String selectedWord) {
        if (isDark) {
            defView.getEngine().setUserStyleSheetLocation(getClass().getResource("darkwebview.css").toString());
        }
        if (isAV == true) {
            defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
        } else {
            defView.getEngine().loadContent(dictDB.getVieDef(selectedWord), "text/html");
        }
        System.gc();
    }

    @FXML
    public void switchDict() {
        if (isAV == true) {
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
        selectedWord = sb.toString();
        if (isAV == true) {
            dictDB.deleteEngWord(selectedWord);
            dictDB.listAV(words);
        } else {
            dictDB.deleteVieWord(selectedWord);
            dictDB.listVA(words);
        }
        System.gc();
    }

    @FXML
    public void autoCompleteListener() {
        if (isAV == true) {
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
        if (isAV == true && selectedWord != "") {
            tts.mp3("en-US", selectedWord);
            Media output = new Media(new File("ttsOutput.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(output);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        } else if (isAV == false && selectedWord != "") {
            tts.mp3("vi-VN", selectedWord);
            Media output = new Media(new File("ttsOutput.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(output);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        }
        System.gc();
    }

    public void handleCloseBTN(ActionEvent event) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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

    public void handleHomeMenuBTN() throws IOException {
        isFav = false;
        isHistory = false;

        if (isAV) {
            dictDB.listAV(words);
            wordsList.setItems(words);
        } else {
            dictDB.listVA(words);
            wordsList.setItems(words);
        }
    }

    public void handleFavoriteMenuBTN() throws IOException {
        isFav = true;
        isHistory = false;

        dictDB.listFav(favWords);
        wordsList.setItems(favWords);
    }

    public void handleTranslateMenuBTN() {

    }

    public void handleSettingsMenuBTN() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWordsList();
        switchUI(isDark);
    }


}
