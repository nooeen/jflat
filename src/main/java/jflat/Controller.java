package jflat;

import javafx.beans.Observable;
import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Label;



public class Controller {
    public static Stage addWindow = new Stage();
    public static Stage favoriteList = new Stage();
    @FXML
    private Label word = new Label();
    @FXML
    private AutoCompleteTextField<String> searchWord;

    @FXML
    private TextArea translation = new TextArea();
    @FXML
    ObservableList<String> obl = FXCollections.observableArrayList();
    @FXML
    ListView<String> FavoriteWord;
    @FXML
    TabPane meaning;
    @FXML
    TextArea Meaning;




    public Controller() {

    }


    public void addButton() throws Exception {
        //System.out.println(word.getText());
        Parent root = FXMLLoader.load(getClass().getResource("add.fxml"));
        addWindow.setTitle("addWord");
        addWindow.setScene(new Scene(root, 300, 200));
        addWindow.show();

    }


    public void DoneButtonAddWord() {
        addWindow.close();
    }

    public void DoneButtonFavoriteWord() {
        favoriteList.close();
    }

    public void EnterButton(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER")) {
            word.setText(searchWord.getText());
            translation.setText(searchWord.getText());
        }
    }

    public void FavoriteListButton() throws Exception {
        //FavoriteWord.getItems().addAll("home", "star", "drug");
        Parent root = FXMLLoader.load(getClass().getResource("Favorite.fxml"));
        favoriteList.setTitle("addWord");
        favoriteList.setScene(new Scene(root, 300, 400));
        favoriteList.show();
    }

}

