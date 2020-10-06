package jflat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class JFlatGUIController implements Initializable{
    @FXML
    private ListView listBoxAll = new ListView();

    final ObservableList<String> listAllWords = FXCollections.<String>observableArrayList("test");

    public void initialize(URL url, ResourceBundle rb) {
        listBoxAll.getItems().add("All(listAllWords)");
    }

}
