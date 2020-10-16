package jflat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class JFlatController implements Initializable {

    public Database dictDB = new Database();

    public GCloudTTS tts = new GCloudTTS();

    public ObservableList<String> words = FXCollections.observableArrayList();
    public boolean isAV = true;

    @FXML
    public ListView<String> wordsList;
    @FXML
    public WebView defView;
    @FXML
    public TextField autoCompleteField;
    @FXML
    public Button switchBTN;
    @FXML
    public Button ttsBTN;
    @FXML
    public Button addWord;
    @FXML
    public Button delWord;

    @FXML
    public void initWordsList() {
        dictDB.listAV(words);
        wordsList.setItems(words);
    }

    @FXML
    public void getWordDef() {
        String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
        StringBuilder sb = new StringBuilder(selectedWord);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        selectedWord = sb.toString();
        if (isAV == true) {
            defView.getEngine().loadContent(dictDB.getEngDef(selectedWord), "text/html");
        } else {
            defView.getEngine().loadContent(dictDB.getVieDef(selectedWord), "text/html");
        }
    }

    @FXML
    public void switchDict() {
        if (isAV == true) {
            switchBTN.setText("English - Vietnamese");
            dictDB.listVA(words);
            wordsList.setItems(words);
            isAV = false;
        } else {
            switchBTN.setText("Vietnamese - English");
            dictDB.listAV(words);
            wordsList.setItems(words);
            isAV = true;
        }
    }

    @FXML
    public void deleteWord() {
        String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
        StringBuilder sb = new StringBuilder(selectedWord);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        selectedWord = sb.toString();
        if(isAV == true) {
            dictDB.deleteEngWord(selectedWord);
            dictDB.listAV(words);
        } else {
            dictDB.deleteVieWord(selectedWord);
            dictDB.listVA(words);
        }
    }

    @FXML
    public void autoCompleteListener() {
        if (isAV == true) {
            dictDB.listAutoCompleteAV(words, autoCompleteField.getText());
        } else {
            dictDB.listAutoCompleteVA(words, autoCompleteField.getText());
        }
    }

    @FXML
    public void ttsPlay() throws Exception {
        String selectedWord = wordsList.getSelectionModel().getSelectedItems().toString();
        StringBuilder sb = new StringBuilder(selectedWord);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length() - 1);
        selectedWord = sb.toString();
        if(isAV == true && selectedWord != "") {
            tts.mp3("en-US", selectedWord);
            Media output = new Media(new File("ttsOutput.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(output);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        } else if(isAV == false && selectedWord != "") {
            tts.mp3("vi-VN", selectedWord);
            Media output = new Media(new File("ttsOutput.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(output);
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWordsList();
    }


}
